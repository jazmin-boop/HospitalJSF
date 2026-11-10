$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
Add-Type -AssemblyName System.Drawing

$templatePath = "C:\Users\USER\Downloads\Tarea_16_actualizada.docx"
$projectDir   = "C:\Users\USER\.gemini\antigravity\scratch\HospitalJSF"
$capturasDir  = Join-Path $projectDir "capturas"
$outProject   = Join-Path $projectDir "Tarea_17.docx"
$outDownloads = "C:\Users\USER\Downloads\Tarea_17.docx"

if (-not (Test-Path $capturasDir)) {
    New-Item -ItemType Directory -Path $capturasDir -Force | Out-Null
}

# Copy template to destination
Copy-Item -Path $templatePath -Destination $outProject -Force

$zip = [System.IO.Compression.ZipFile]::Open($outProject, [System.IO.Compression.ZipArchiveMode]::Update)

# 1. Update docProps/core.xml
$coreEntry = $zip.GetEntry("docProps/core.xml")
$reader = New-Object System.IO.StreamReader($coreEntry.Open())
$coreXml = $reader.ReadToEnd()
$reader.Close()

$coreXml = $coreXml -replace "SISTEMA DE GESTIÓN DE EMPLEADOS", "SISTEMA HOSPITALARIO WEB"
$coreXml = $coreXml -replace "Tarea 16 — Aplicación web con Servlets, JSP, Hibernate y MySQL", "Tarea 17 — Aplicación web con JSF 2.2, ManagedBeans, CDI, Hibernate y MySQL"

$coreEntry.Delete()
$newCore = $zip.CreateEntry("docProps/core.xml")
$writer = New-Object System.IO.StreamWriter($newCore.Open(), [System.Text.Encoding]::UTF8)
$writer.Write($coreXml)
$writer.Close()

# 2. Update customXml/item1.xml
$cxEntry = $zip.GetEntry("customXml/item1.xml")
if ($cxEntry) {
    $reader = New-Object System.IO.StreamReader($cxEntry.Open())
    $cxXml = $reader.ReadToEnd()
    $reader.Close()

    $cxXml = $cxXml -replace "Octubre de 2026", "Noviembre de 2026"

    $cxEntry.Delete()
    $newCx = $zip.CreateEntry("customXml/item1.xml")
    $writer = New-Object System.IO.StreamWriter($newCx.Open(), [System.Text.Encoding]::UTF8)
    $writer.Write($cxXml)
    $writer.Close()
}

# 3. Read word/document.xml and word/_rels/document.xml.rels
$docEntry = $zip.GetEntry("word/document.xml")
$reader = New-Object System.IO.StreamReader($docEntry.Open())
[xml]$doc = $reader.ReadToEnd()
$reader.Close()

$relsEntry = $zip.GetEntry("word/_rels/document.xml.rels")
$reader = New-Object System.IO.StreamReader($relsEntry.Open())
[xml]$rels = $reader.ReadToEnd()
$reader.Close()

# Remove old media/image1..image37 from zip (keep image71.png and image72.png which are used by headers)
$oldImages = $zip.Entries | Where-Object { $_.FullName -match "^word/media/image([1-9]|[1-3][0-9])\.png$" }
foreach ($img in @($oldImages)) {
    $img.Delete()
}

# Clean old image relationships in document.xml.rels
$relNs = New-Object System.Xml.XmlNamespaceManager($rels.NameTable)
$relNs.AddNamespace("r", "http://schemas.openxmlformats.org/package/2006/relationships")
$imgRels = $rels.SelectNodes("//r:Relationship[contains(@Type, '/image')]", $relNs)
foreach ($ir in $imgRels) {
    [void]$ir.ParentNode.RemoveChild($ir)
}

$ns = New-Object System.Xml.XmlNamespaceManager($doc.NameTable)
$ns.AddNamespace("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main")
$body = $doc.SelectSingleNode("//w:body", $ns)

# Keep cover page (node 0) and sectPr (last node)
$coverPage = $body.SelectSingleNode("w:sdt", $ns).CloneNode($true)
$sectPr    = $body.SelectSingleNode("w:sectPr", $ns).CloneNode($true)

# Update cover page text
$coverXml = $coverPage.OuterXml
$coverXml = $coverXml -replace "Octubre de 2026", "Noviembre de 2026"
$coverXml = $coverXml -replace "SISTEMA DE GESTIÓN DE EMPLEADOS", "SISTEMA HOSPITALARIO WEB"
$coverXml = $coverXml -replace "Tarea 16 — Aplicación web con Servlets, JSP, Hibernate y MySQL", "Tarea 17 — Aplicación web con JSF 2.2, ManagedBeans, CDI, Hibernate y MySQL"

# Clear body
$body.RemoveAll()

# Helper functions to create Word XML elements matching Tarea 16 exact styles
function Escape-Xml($s) {
    if ($null -eq $s) { return "" }
    return [System.Security.SecurityElement]::Escape($s)
}

function Add-RawXml($xmlString) {
    $frag = $doc.CreateDocumentFragment()
    $frag.InnerXml = $xmlString
    [void]$body.AppendChild($frag)
}

function Add-Heading1($text) {
    $t = Escape-Xml $text
    $xml = @"
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:pPr>
    <w:pStyle w:val="Ttulo1" />
    <w:jc w:val="center" />
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="44" />
      <w:szCs w:val="32" />
      <w:lang w:val="es-PE" />
    </w:rPr>
  </w:pPr>
  <w:r>
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="44" />
      <w:szCs w:val="32" />
      <w:lang w:val="es-PE" />
    </w:rPr>
    <w:t>$t</w:t>
  </w:r>
</w:p>
"@
    Add-RawXml $xml
}

function Add-Heading2($text) {
    $t = Escape-Xml $text
    $xml = @"
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:pPr>
    <w:pStyle w:val="Ttulo2" />
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="32" />
      <w:szCs w:val="28" />
      <w:u w:val="single" />
      <w:lang w:val="es-PE" />
    </w:rPr>
  </w:pPr>
  <w:r>
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="32" />
      <w:szCs w:val="28" />
      <w:u w:val="single" />
      <w:lang w:val="es-PE" />
    </w:rPr>
    <w:t xml:space="preserve">$t</w:t>
  </w:r>
</w:p>
"@
    Add-RawXml $xml
}

function Add-Heading3($text) {
    $t = Escape-Xml $text
    $xml = @"
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:pPr>
    <w:pStyle w:val="Ttulo3" />
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="28" />
      <w:szCs w:val="24" />
      <w:lang w:val="es-PE" />
    </w:rPr>
  </w:pPr>
  <w:r>
    <w:rPr>
      <w:rFonts w:asciiTheme="minorHAnsi" w:hAnsiTheme="minorHAnsi" />
      <w:color w:val="153D63" w:themeColor="text2" w:themeTint="E6" />
      <w:sz w:val="28" />
      <w:szCs w:val="24" />
      <w:lang w:val="es-PE" />
    </w:rPr>
    <w:t xml:space="preserve">$t</w:t>
  </w:r>
</w:p>
"@
    Add-RawXml $xml
}

# Supports simple inline markup: **bold** and `code`
function Add-Paragraph($text) {
    $runsXml = ""
    $pattern = '(\*\*[^*]+\*\*|`[^`]+`)'
    $parts = [regex]::Split($text, $pattern)
    foreach ($part in $parts) {
        if ($part -eq "") { continue }
        if ($part.StartsWith("**") -and $part.EndsWith("**")) {
            $inner = Escape-Xml ($part.Substring(2, $part.Length - 4))
            $runsXml += "<w:r><w:rPr><w:b/><w:bCs/></w:rPr><w:t xml:space=`"preserve`">$inner</w:t></w:r>"
        } elseif ($part.StartsWith("``") -and $part.EndsWith("``")) {
            $inner = Escape-Xml ($part.Substring(1, $part.Length - 2))
            $runsXml += "<w:r><w:rPr><w:rStyle w:val=`"CodeChar`"/></w:rPr><w:t xml:space=`"preserve`">$inner</w:t></w:r>"
        } else {
            $inner = Escape-Xml $part
            $runsXml += "<w:r><w:t xml:space=`"preserve`">$inner</w:t></w:r>"
        }
    }
    $xml = "<w:p xmlns:w=`"http://schemas.openxmlformats.org/wordprocessingml/2006/main`">$runsXml</w:p>"
    Add-RawXml $xml
}

$script:imgCounter = 1
function Add-Screenshot($fileName, $caption) {
    Add-Paragraph $caption
    $fullImgPath = Join-Path $capturasDir $fileName
    if (Test-Path $fullImgPath) {
        $idx = $script:imgCounter
        $script:imgCounter++
        $rId = "rIdImg$idx"
        $mediaName = "media/captura_$idx.png"
        $zipEntryName = "word/$mediaName"

        # Add image to zip
        $existing = $zip.GetEntry($zipEntryName)
        if ($existing) { $existing.Delete() }
        [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, $fullImgPath, $zipEntryName) | Out-Null

        # Add relationship
        $relElem = $rels.CreateElement("Relationship", "http://schemas.openxmlformats.org/package/2006/relationships")
        $relElem.SetAttribute("Id", $rId)
        $relElem.SetAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image")
        $relElem.SetAttribute("Target", $mediaName)
        [void]$rels.DocumentElement.AppendChild($relElem)

        # Calculate proportional EMU size (max width ~ 5800000 EMUs = ~6.34 inches)
        $imgObj = [System.Drawing.Image]::FromFile($fullImgPath)
        $wPx = $imgObj.Width
        $hPx = $imgObj.Height
        $imgObj.Dispose()

        $maxCx = 5800000
        $maxCy = 4200000
        $cx = [int]($wPx * 9525)
        $cy = [int]($hPx * 9525)
        if ($cx -gt $maxCx) {
            $ratio = $maxCx / [double]$cx
            $cx = $maxCx
            $cy = [int]($cy * $ratio)
        }
        if ($cy -gt $maxCy) {
            $ratio = $maxCy / [double]$cy
            $cy = $maxCy
            $cx = [int]($cx * $ratio)
        }

        $docPrId = 1000 + $idx
        $xml = @"
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:pPr><w:jc w:val="center" /></w:pPr>
  <w:r>
    <w:drawing>
      <wp:inline distT="0" distB="0" distL="0" distR="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
        <wp:extent cx="$cx" cy="$cy" />
        <wp:effectExtent l="0" t="0" r="0" b="0" />
        <wp:docPr id="$docPrId" name="Captura $idx" />
        <wp:cNvGraphicFramePr>
          <a:graphicFrameLocks xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" noChangeAspect="1" />
        </wp:cNvGraphicFramePr>
        <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
          <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
              <pic:nvPicPr>
                <pic:cNvPr id="$docPrId" name="$fileName" />
                <pic:cNvPicPr />
              </pic:nvPicPr>
              <pic:blipFill>
                <a:blip r:embed="$rId" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" />
                <a:stretch><a:fillRect /></a:stretch>
              </pic:blipFill>
              <pic:spPr>
                <a:xfrm><a:off x="0" y="0" /><a:ext cx="$cx" cy="$cy" /></a:xfrm>
                <a:prstGeom prst="rect"><a:avLst /></a:prstGeom>
              </pic:spPr>
            </pic:pic>
          </a:graphicData>
        </a:graphic>
      </wp:inline>
    </w:drawing>
  </w:r>
</w:p>
"@
        Add-RawXml $xml
    } else {
        # Styled placeholder box indicating exact filename to place in capturas/
        $fEsc = Escape-Xml $fileName
        $xml = @"
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:pPr>
    <w:jc w:val="center" />
    <w:pBdr>
      <w:top w:val="dashed" w:sz="6" w:space="6" w:color="94A3B8" />
      <w:left w:val="dashed" w:sz="6" w:space="6" w:color="94A3B8" />
      <w:bottom w:val="dashed" w:sz="6" w:space="6" w:color="94A3B8" />
      <w:right w:val="dashed" w:sz="6" w:space="6" w:color="94A3B8" />
    </w:pBdr>
    <w:shd w:val="clear" w:color="auto" w:fill="F8FAFC" />
  </w:pPr>
  <w:r>
    <w:rPr><w:b/><w:color w:val="1E40AF"/><w:sz w:val="20"/></w:rPr>
    <w:t xml:space="preserve">[ Espacio para captura: capturas/$fEsc ]</w:t>
  </w:r>
</w:p>
"@
        Add-RawXml $xml
    }
}

function Add-Table($headers, $widths, $rows, $centerCols = @()) {
    $gridCols = ""
    foreach ($w in $widths) {
        $gridCols += "<w:gridCol w:w=`"$w`" />"
    }

    $headerCells = ""
    for ($i = 0; $i -lt $headers.Count; $i++) {
        $hText = Escape-Xml $headers[$i]
        $w = $widths[$i]
        $jc = if ($centerCols -contains $i) { "<w:pPr><w:jc w:val=`"center`"/></w:pPr>" } else { "" }
        $headerCells += @"
<w:tc>
  <w:tcPr><w:tcW w:w="$w" w:type="dxa"/><w:shd w:val="clear" w:color="auto" w:fill="F2F2F2"/></w:tcPr>
  <w:p>$jc<w:r><w:rPr><w:b/></w:rPr><w:t>$hText</w:t></w:r></w:p>
</w:tc>
"@
    }

    $rowsXml = ""
    foreach ($row in $rows) {
        $cellsXml = ""
        for ($i = 0; $i -lt $row.Count; $i++) {
            $cellText = Escape-Xml $row[$i]
            $w = $widths[$i]
            $jc = if ($centerCols -contains $i) { "<w:pPr><w:jc w:val=`"center`"/></w:pPr>" } else { "" }
            $cellsXml += @"
<w:tc>
  <w:tcPr><w:tcW w:w="$w" w:type="dxa"/></w:tcPr>
  <w:p>$jc<w:r><w:t xml:space="preserve">$cellText</w:t></w:r></w:p>
</w:tc>
"@
        }
        $rowsXml += "<w:tr>$cellsXml</w:tr>"
    }

    $tblXml = @"
<w:tbl xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:tblPr>
    <w:tblStyle w:val="Tablaconcuadrcula"/>
    <w:tblW w:w="0" w:type="auto"/>
    <w:tblLayout w:type="fixed"/>
    <w:tblLook w:val="0420" w:firstRow="1" w:lastRow="0" w:firstColumn="0" w:lastColumn="0" w:noHBand="0" w:noVBand="1"/>
  </w:tblPr>
  <w:tblGrid>$gridCols</w:tblGrid>
  <w:tr><w:trPr><w:tblHeader/></w:trPr>$headerCells</w:tr>
  $rowsXml
</w:tbl>
<w:p xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"/>
"@
    Add-RawXml $tblXml
}

# ================= BUILD DOCUMENT CONTENT =================

# 0. Cover Page
Add-RawXml $coverXml

# Main Title
Add-Heading1 "SISTEMA HOSPITALARIO WEB"

# 1. Introducción
Add-Heading2 '1. Introducción'
Add-Paragraph 'HospitalJSF es una aplicación web empresarial hospitalaria desarrollada con **JavaServer Faces (JSF 2.2)**, **ManagedBeans**, **Inyección de Dependencias (CDI)**, **Servicios**, **DAO**, **PrimeFaces** y **MySQL** (`hospitalbd`) para administrar de forma integral **pacientes**, **doctores** y **consultas médicas** (diagnósticos, tratamientos y análisis clínicos). Incluye un **dashboard hospitalario** con indicadores de triaje y flujo de atención médica en vivo, validadores y convertidores nativos del ciclo de vida JSF, autenticación con control de acceso por roles (**ADMIN** y **DOCTOR**), **historial clínico** por paciente, **calendario interactivo de citas** y generación de **reportes en PDF**.'

Add-Heading3 'Programas y tecnologías utilizadas'
Add-Paragraph 'La siguiente tabla resume las herramientas y tecnologías empleadas conforme a los requisitos de la práctica de la Semana 12:'
Add-Table -headers @('Programa / Tecnología', 'Versión', 'Función en el Proyecto') -widths @(3200, 1800, 4360) -centerCols @(1) -rows @(
    @('JDK (Java Development Kit)', '1.8 (Java 8)', 'Compilar y ejecutar el código Java de la aplicación'),
    @('Apache NetBeans', 'IDE', 'Entorno de desarrollo para programar y desplegar el proyecto'),
    @('GlassFish / Payara Server', 'Java EE 7 Web', 'Servidor de aplicaciones compatible con JSF 2.2 y CDI'),
    @('JSF (JavaServer Faces)', '2.2 (Mojarra)', 'Framework web MVC basado en componentes, ciclo de vida y Facelets'),
    @('CDI (Weld)', '1.2 / 2.0', 'Inyección de dependencias (@Inject, @Named y Scopes)'),
    @('MySQL + XAMPP / phpMyAdmin', '8.0 / MariaDB', 'Gestor y administración de la base de datos relacional hospitalbd'),
    @('Hibernate ORM + JDBC', '5.6 / 8.0.28', 'Persistencia de datos mediante DAO, PreparedStatement y ORM'),
    @('PrimeFaces + OpenPDF', '12.0 / 1.3.30', 'Calendario interactivo de citas (p:schedule) y reportes PDF')
)

# 2. Arquitectura Obligatoria y Ciclo de Vida JSF
Add-Heading2 '2. Arquitectura Obligatoria'
Add-Paragraph 'Esta sección explica cómo se organiza el Sistema Hospitalario en capas desacopladas, cómo opera el ciclo de vida JSF 2.2 y qué archivos de configuración permiten su ejecución.'

Add-Heading3 'Patrón arquitectónico: MVC + ManagedBeans + Servicios + DAO en capas'
Add-Paragraph 'La aplicación sigue el patrón **Modelo–Vista–Controlador (MVC)** propio de JSF 2.2 y separa la lógica de negocio y el acceso a datos en capas (**ManagedBeans**, **Servicios** y **DAO**). Piensa en la atención dentro de un hospital: el paciente llega a la recepción (**Vista XHTML / Facelets**) e ingresa sus datos; el coordinador clínico (**ManagedBean** con `@Named` y `@SessionScoped`) recibe la información ya validada por JSF y la transfiere mediante inyección de dependencias (`@Inject`) al departamento médico (**Capa de Servicio** con `@ApplicationScoped`), el cual se apoya en el archivo central (**DAO + Conexion JDBC / Hibernate**) que es el único que interactúa directamente con la base de datos MySQL (`hospitalbd`). Finalmente, el coordinador devuelve un mensaje de confirmación (`FacesMessage`) a la pantalla.'

Add-Heading3 'Ciclo de Vida JSF en el Proyecto'
Add-Paragraph 'Cada formulario del sistema (`paciente.xhtml`, `doctor.xhtml` y `consulta.xhtml`) atraviesa las fases del ciclo de vida de **JSF 2.2**: (1) **Restaurar Vista**: el usuario abre el formulario y JSF construye el árbol de componentes; (2) **Aplicar Valores de Solicitud**: JSF recupera el texto escrito en los `<h:inputText>`; (3) **Procesar Validaciones y Conversiones**: los validadores (`<f:validateLength>`, `<f:validateLongRange>`) verifican nombre, edad y DNI, y el convertidor (`<f:convertDateTime pattern="dd/MM/yyyy"/>`) transforma la cadena de fecha en un objeto `java.util.Date`; (4) **Actualizar Valores del Modelo**: los datos validados pasan automáticamente a las propiedades de `PacienteBean`, `DoctorBean` o `ConsultaBean`; (5) **Invocar Aplicación**: el botón `<h:commandButton>` ejecuta el método del Bean (`guardar()`), que invoca a `PacienteServicio` inyectado con `@Inject`; (6) **Persistencia DAO**: el DAO ejecuta el `PreparedStatement` en MySQL; y (7) **Renderizar Respuesta**: JSF actualiza la tabla y muestra el aviso en `<h:messages/>`.'

Add-Heading3 'Estructura del proyecto'
Add-Screenshot '01_estructura_proyecto.png' 'Organización del proyecto HospitalJSF: los paquetes `beans`, `conexion`, `dao`, `filtro`, `modelo` y `servicios` contienen el código Java en capas; `webapp` aloja las vistas XHTML (`login.xhtml`, `index.xhtml`, `paciente.xhtml`, `doctor.xhtml`, `consulta.xhtml`, `historial.xhtml`, `calendario.xhtml`), `WEB-INF` (`beans.xml`, `faces-config.xml`, `web.xml`) y `resources/css/style.css`; y `pom.xml` define las dependencias Maven.'

Add-Heading3 'Configuración del proyecto'
Add-Paragraph 'Archivos que definen las dependencias del proyecto, la conexión con MySQL y la activación de JSF 2.2 e inyección de dependencias CDI.'
Add-Screenshot '02_pom_xml.png' '`pom.xml` (dependencias principales): configura Java 1.8, JSF 2.2 (Mojarra), CDI (Weld), PrimeFaces 12, Hibernate Core, OpenPDF y `mysql-connector-java`.'
Add-Screenshot '03_conexion_java.png' '`Conexion.java` y `HibernateUtil.java`: establecen la conexión JDBC mediante `DriverManager.getConnection` hacia `jdbc:mysql://localhost:3306/hospitalbd` e inicializan el `SessionFactory` de Hibernate.'
Add-Screenshot '04_web_faces_config.png' '`web.xml`, `faces-config.xml` y `beans.xml`: configuran el `FacesServlet` mapeado a `*.xhtml`, definen `login.xhtml` como página de inicio y habilitan la inyección de dependencias CDI (`bean-discovery-mode="all"`).'

# 3. Base de Datos MySQL
Add-Heading2 '3. Base de Datos MySQL'
Add-Paragraph 'La base de datos `hospitalbd` está compuesta por cuatro tablas: `usuario`, `paciente`, `doctor` y `consulta`, relacionadas mediante claves foráneas con integridad referencial.'
Add-Screenshot '05_diagrama_er_mysql.png' 'Diagrama entidad-relación de `hospitalbd` en phpMyAdmin / MySQL: un **paciente** puede registrar múltiples **consultas** (`idPaciente`) y un **doctor** atiende múltiples **consultas** (`idDoctor`); la tabla `usuario` gestiona el acceso y el rol (`ADMIN` o `DOCTOR`).'

Add-Heading3 'Script completo (database.sql)'
Add-Paragraph 'Script SQL que crea la base de datos `hospitalbd`, las tablas requeridas por la práctica (`paciente`, `doctor`, `consulta`) junto con `usuario`, e inserta los registros iniciales.'
Add-Screenshot '06_database_sql_tablas1.png' 'Creación de la base de datos `hospitalbd` y de las tablas `usuario`, `paciente` (idPaciente, nombre, edad, dni, sexo, telefono) y `doctor` (idDoctor, nombre, especialidad, telefono, email).'
Add-Screenshot '07_database_sql_tablas2.png' 'Creación de la tabla `consulta` (idConsulta, diagnostico, tratamiento, analisis, fecha, idPaciente, idDoctor) con sus claves foráneas (`FOREIGN KEY`) y carga de datos semilla (`Carlos Perez - Cardiologia`, `Ana Torres - Pediatria`, usuarios `admin` y `medico`).'

# 4. Seguridad y Roles
Add-Heading2 '4. Seguridad y Roles'
Add-Paragraph 'El acceso al sistema se protege con inicio de sesión (`LoginBean.java`) y un filtro web (`SeguridadFilter.java`) que implementa control de acceso basado en roles (**RBAC**) con dos perfiles: **ADMIN** (administrador hospitalario con acceso total, incluido el módulo de Doctores y la asignación de cualquier médico en consultas) y **DOCTOR** (médico tratante que no puede acceder al mantenimiento de doctores y que en el módulo de Consultas atiende exclusivamente bajo su propio código de médico). La tabla resume los permisos de cada rol:'

Add-Table -headers @('Acción / Funcionalidad', 'Vista / Ruta XHTML', 'ADMIN', 'DOCTOR') -widths @(3632, 2994, 1100, 1634) -centerCols @(2, 3) -rows @(
    @('Iniciar / cerrar sesión', '/login.xhtml', 'Sí', 'Sí'),
    @('Ver Dashboard Hospitalario y métricas de triaje', '/index.xhtml', 'Sí', 'Sí'),
    @('Registrar, listar, editar y eliminar pacientes', '/paciente.xhtml', 'Sí', 'Sí'),
    @('Exportar reporte general de pacientes en PDF', '/paciente.xhtml (PDF)', 'Sí', 'Sí'),
    @('Gestionar doctores (crear, editar, eliminar)', '/doctor.xhtml', 'Sí', 'No (Oculto y redirigido)'),
    @('Registrar consulta médica seleccionando cualquier doctor', '/consulta.xhtml', 'Sí', 'No (Solo él mismo)'),
    @('Registrar consulta médica como médico logueado', '/consulta.xhtml', 'Sí', 'Sí (Autoasignado)'),
    @('Consultar Historial Médico y exportar PDF clínico', '/historial.xhtml', 'Sí', 'Sí'),
    @('Visualizar Calendario y Agenda de Citas (PrimeFaces)', '/calendario.xhtml', 'Sí', 'Sí')
)

Add-Heading3 'Autenticación y Filtro de Acceso'
Add-Paragraph '`LoginBean` (`@Named`, `@SessionScoped`) recibe el usuario y contraseña desde `login.xhtml`, consulta a `UsuarioDAO.autenticar()` a través de `PacienteServicio` y guarda el usuario autenticado en la sesión HTTP.'
Add-Screenshot '08_login_bean_java.png' '`LoginBean.java` y `UsuarioDAO.java`: validan las credenciales en MySQL, verifican si el rol es `ADMIN` o `DOCTOR`, abren la sesión y muestran un `FacesMessage` en caso de error.'
Add-Screenshot '09_seguridad_filter_java.png' '`SeguridadFilter.java` (`@WebFilter("*.xhtml")`): bloquea el acceso sin sesión redirigiendo a `login.xhtml` e impide que un usuario sin rol `ADMIN` ingrese a `doctor.xhtml`.'
Add-Screenshot '10_vista_login.png' 'Pantalla de inicio de sesión (`login.xhtml`) con las credenciales de prueba (`admin / admin123` y `medico / medico123`).'

Add-Heading3 'Autorización por rol en la interfaz'
Add-Paragraph 'Según el rol de la sesión activa (`#{loginBean.admin}` y `#{loginBean.doctor}`), Facelets renderiza u oculta dinámicamente las opciones restringidas.'
Add-Screenshot '11_vista_rol_admin.png' 'Vista **ADMIN** (`Dr. Carlos Pérez - ADMIN`): muestra el módulo «Doctores» en el menú lateral y permite elegir cualquier médico tratante en el formulario de consultas.'
Add-Screenshot '12_vista_rol_doctor.png' 'Vista **DOCTOR** (`Dra. Ana Torres - DOCTOR`): oculta el módulo «Doctores» del menú lateral y fija automáticamente al médico logueado como Médico Tratante en el registro de consultas.'

# 5. Módulo Dashboard Hospitalario
Add-Heading2 '5. Módulo Dashboard Hospitalario'
Add-Paragraph 'Panel principal (`index.xhtml`) que centraliza la operación clínica del hospital: muestra la tarjeta de **Atención Médica en Curso** con el flujo visual **Médico Tratante ➔ Paciente Atendido**, las barras de estadística de triaje y atención en línea, las tarjetas de métricas (`Pacientes Registrados`, `Staff Médico`, `Consultas`, `Calidad Asistencial`) y la tabla rápida de pacientes con enlace directo a su historia clínica.'
Add-Screenshot '13_vista_dashboard.png' 'Vista del Dashboard Hospitalario (`index.xhtml`): flujo de atención médica en curso, indicadores clínicos, tabla de pacientes registrados y botón superior para exportar PDF.'

# 6. Módulo Pacientes (ManagedBean, Validadores, Servicio, Inyección CDI y DAO)
Add-Heading2 '6. Módulo Pacientes'
Add-Paragraph 'Implementa el registro y mantenimiento completo (CRUD) de pacientes (`idPaciente`, `nombre`, `edad`, `dni`, `sexo`, `telefono`), aplicando todos los componentes exigidos en la práctica de la Semana 12: **Modelo**, **DAO**, **Servicio**, **Inyección de Dependencias (`@Inject`)**, **ManagedBean (`@Named`, `@SessionScoped`)**, **Validadores JSF** y **Mensajes (`FacesMessage`)**.'

Add-Heading3 'Modelo y Capa DAO (Paciente.java y PacienteDAO.java)'
Add-Screenshot '14_paciente_dao_java.png' '`PacienteDAO.java`: ejecuta las sentencias parametrizadas con `PreparedStatement` (`INSERT INTO paciente`, `SELECT`, `UPDATE` y `DELETE`) sobre la tabla `paciente` en `hospitalbd`.'

Add-Heading3 'Capa de Servicio e Inyección de Dependencias (PacienteServicio.java y PacienteBean.java)'
Add-Screenshot '15_paciente_servicio_java.png' '`PacienteServicio.java` (`@ApplicationScoped`): concentra la lógica de negocio hospitalaria y coordina las llamadas hacia `PacienteDAO`, `DoctorDAO`, `ConsultaDAO` y `UsuarioDAO`.'
Add-Screenshot '16_paciente_bean_java.png' '`PacienteBean.java` (`@Named`, `@SessionScoped`): inyecta `PacienteServicio` y `ReportePDFServicio` mediante `@Inject`, procesa el método `guardar()` y envía la notificación al usuario con `FacesContext.getCurrentInstance().addMessage(...)`.'

Add-Heading3 'Vista XHTML y Validadores JSF (paciente.xhtml)'
Add-Screenshot '17_paciente_xhtml_codigo.png' '`paciente.xhtml`: formulario JSF con validadores `<f:validateLength minimum="3" maximum="50"/>` en nombre, `<f:validateLongRange minimum="1" maximum="100"/>` en edad, `<f:validateLength minimum="8" maximum="8"/>` en DNI, `<h:selectOneMenu>` para sexo y `<h:messages/>`.'
Add-Screenshot '18_vista_pacientes.png' 'Pantalla «Gestión de Pacientes» en el navegador: formulario con validadores JSF activos, mensaje de confirmación al guardar en MySQL y tabla inferior con acciones de Editar y Eliminar.'

# 7. Módulo Doctores
Add-Heading2 '7. Módulo Doctores'
Add-Paragraph 'Permite registrar, listar, actualizar y eliminar a los médicos especialistas del hospital (`idDoctor`, `nombre`, `especialidad`, `telefono`, `email`). Este módulo está disponible únicamente para el rol **ADMIN**.'
Add-Screenshot '19_doctor_bean_dao_java.png' '`DoctorBean.java` (`@Named`, `@SessionScoped`) y `DoctorDAO.java`: gestionan el CRUD de médicos especialistas mediante inyección de `PacienteServicio` y consultas `PreparedStatement`.'
Add-Screenshot '20_vista_doctores.png' 'Pantalla «Registro de Doctores» (`doctor.xhtml`): alta y edición de médicos con su especialidad (`Cardiología`, `Pediatría`, etc.) y listado actualizado desde MySQL.'

# 8. Módulo Consultas Médicas y Convertidores JSF
Add-Heading2 '8. Módulo Consultas Médicas y Convertidores JSF'
Add-Paragraph 'Permite registrar las atenciones médicas vinculando al **Paciente** y al **Médico Tratante**, e ingresando el **diagnóstico**, el **tratamiento / receta médica**, los **análisis clínicos**, la **fecha** y el **estado** de la consulta (`Atendida`, `En Espera`, `Programada`).'
Add-Screenshot '21_consulta_bean_dao_java.png' '`ConsultaBean.java` y `ConsultaDAO.java`: guardan la consulta médica en MySQL y listan el historial general mediante `LEFT JOIN` entre `consulta`, `paciente` y `doctor`.'

Add-Heading3 'Convertidor de Fecha (<f:convertDateTime>)'
Add-Screenshot '22_consulta_xhtml_convertidor.png' '`consulta.xhtml`: aplicación del convertidor `<f:convertDateTime pattern="dd/MM/yyyy"/>` tanto en la caja de texto `<h:inputText>` de la fecha de atención como en la tabla `<h:outputText>` para convertir entre `String` y `java.util.Date`.'
Add-Screenshot '23_vista_consultas.png' 'Pantalla «Registro de Consulta» (`consulta.xhtml`): formulario clínico completo con selección de paciente, médico tratante, fecha con convertidor JSF, diagnóstico, tratamiento, análisis y tabla de consultas registradas.'

# 9. Módulo Historial Médico y Calendario de Citas
Add-Heading2 '9. Módulo Historial Médico y Calendario de Citas'
Add-Paragraph 'Complementa el flujo clínico permitiendo consultar el expediente completo de cada paciente y visualizar todas las citas médicas programadas en un calendario interactivo.'
Add-Screenshot '24_vista_historial_medico.png' 'Pantalla «Historial Médico del Paciente» (`historial.xhtml` + `HistorialMedicoBean.java`): muestra la ficha clínica del paciente seleccionado y la tabla cronológica con sus diagnósticos, tratamientos y análisis clínicos.'
Add-Screenshot '25_vista_calendario.png' 'Pantalla «Calendario y Agenda Hospitalaria» (`calendario.xhtml` + `CalendarioBean.java`): integra el componente `<p:schedule>` de PrimeFaces alimentado por `DefaultScheduleModel` con las consultas registradas en MySQL.'

# 10. Módulo de Exportación de Reportes PDF
Add-Heading2 '10. Módulo de Exportación de Reportes PDF'
Add-Paragraph 'Permite descargar en formato PDF tanto el padrón general de pacientes (`Reporte_Pacientes.pdf`) como el expediente clínico individual de un paciente (`Historial_Medico_DNI.pdf`) utilizando la librería **OpenPDF**.'
Add-Screenshot '26_reporte_pdf_servicio_java.png' '`ReportePDFServicio.java` (`@ApplicationScoped`): configura las cabeceras HTTP `application/pdf` mediante `ExternalContext` y construye las tablas estilizadas con `PdfPTable` y `PdfWriter`.'
Add-Screenshot '27_vista_descarga_pdf.png' 'Descarga y visualización del archivo PDF generado desde el Sistema Hospitalario (`Reporte_Pacientes.pdf` / `Historial_Medico.pdf`).'

# 11. Cumplimiento de Temas y Conclusión
Add-Heading2 '11. Cumplimiento de Requisitos y Conclusión'
Add-Paragraph 'La siguiente tabla verifica el cumplimiento de todos los temas exigidos en la práctica de la Semana 12, así como las mejoras empresariales incorporadas en `HospitalJSF`:'

Add-Table -headers @('Tema / Requisito de la Práctica', 'Implementación en HospitalJSF', 'Aplicado') -widths @(3200, 4960, 1200) -centerCols @(2) -rows @(
    @('JSF 2.2 y Facelets XHTML', 'Configurado en faces-config.xml, web.xml y 7 vistas .xhtml', 'Sí'),
    @('ManagedBeans y Scopes', 'PacienteBean, DoctorBean, ConsultaBean, LoginBean, HistorialMedicoBean, CalendarioBean', 'Sí'),
    @('Modelado de Entidades', 'Clases Paciente, Doctor, Consulta y Usuario en el paquete modelo', 'Sí'),
    @('Validadores JSF', 'f:validateLength (nombre y DNI), f:validateLongRange (edad) y required=true', 'Sí'),
    @('Convertidores JSF', 'f:convertDateTime pattern="dd/MM/yyyy" en consulta.xhtml e historial.xhtml', 'Sí'),
    @('Ciclo de Vida JSF y Mensajes', 'Fases completas de validación, conversión, actualización de modelo y FacesMessage', 'Sí'),
    @('Capa de Servicios', 'PacienteServicio y ReportePDFServicio (@ApplicationScoped)', 'Sí'),
    @('Inyección de Dependencias (CDI)', 'Anotación @Inject y archivo WEB-INF/beans.xml', 'Sí'),
    @('Capa DAO y Base de Datos MySQL', 'PacienteDAO, DoctorDAO, ConsultaDAO, UsuarioDAO sobre hospitalbd', 'Sí'),
    @('Mejoras Implementadas', 'Login y Roles (ADMIN/DOCTOR), CRUD Completo, Historial Clínico, Calendario PrimeFaces y PDF', 'Sí')
)

Add-Paragraph '**Conclusión:** El desarrollo del **Sistema Hospitalario Web (HospitalJSF)** permitió integrar de forma práctica todos los fundamentos de **JavaServer Faces (JSF 2.2)**: desde el diseño de vistas XHTML con validadores (`<f:validateLength>`, `<f:validateLongRange>`) y convertidores (`<f:convertDateTime>`), hasta el manejo del ciclo de vida JSF, **ManagedBeans**, **Inyección de Dependencias (`@Inject`)**, **Servicios**, **DAO** y persistencia relacional en **MySQL (`hospitalbd`)**, consolidando una arquitectura web por capas limpia, segura, escalable y alineada a entornos empresariales reales.'

# Append sectPr at the end of body
[void]$body.AppendChild($doc.ImportNode($sectPr, $true))

# Save word/document.xml and word/_rels/document.xml.rels back into the zip
$docEntry.Delete()
$newDocEntry = $zip.CreateEntry("word/document.xml")
$writer = New-Object System.IO.StreamWriter($newDocEntry.Open(), [System.Text.Encoding]::UTF8)
$doc.Save($writer)
$writer.Close()

$relsEntry.Delete()
$newRelsEntry = $zip.CreateEntry("word/_rels/document.xml.rels")
$writer = New-Object System.IO.StreamWriter($newRelsEntry.Open(), [System.Text.Encoding]::UTF8)
$rels.Save($writer)
$writer.Close()

$zip.Dispose()

# Also copy to Downloads
Copy-Item -Path $outProject -Destination $outDownloads -Force

Write-Host "Tarea_17.docx generado exitosamente en:"
Write-Host " - $outProject"
Write-Host " - $outDownloads"
Write-Host "Carpeta de capturas lista en:"
Write-Host " - $capturasDir"
