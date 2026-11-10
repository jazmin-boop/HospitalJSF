# 🏥 Sistema Hospitalario Web - HospitalJSF

Sistema integral de gestión y atención hospitalaria desarrollado en **JavaServer Faces (JSF 2.2 / Mojarra)**, **PrimeFaces 12**, **CDI (Weld)**, **iText PDF** y **MySQL**. Diseñado con una interfaz moderna y responsiva inspirada en plataformas clínicas de alta gama.

---

## 🌟 Características y Módulos

1. **Dashboard General y Flujo Asistencial (`index.xhtml`)**:
   - Tarjetas de estadísticas en tiempo real (pacientes, doctores, citas del día, triajes).
   - Visualización del flujo de atención activa (Médico tratante ➔ Paciente).
   - Padrón rápido de pacientes con acceso directo al historial clínico.

2. **Gestión de Pacientes (`paciente.xhtml`)**:
   - Registro de datos de filiación, domicilio y contacto.
   - Constantes clínicas iniciales: talla, peso, presión arterial, temperatura y registro de alergias.
   - Validadores JSF nativos (`f:validateLength`, `f:validateLongRange`).
   - Exportación de padrón completo de pacientes a PDF.

3. **Módulo de Triaje Clínico (`triaje.xhtml`)**:
   - Evaluación y clasificación de urgencia según la **Escala Manchester** (Prioridad I a IV con distintivos visuales).
   - Monitoreo de signos vitales: PA, FC, FR, T°, SpO2, Antropometría y cálculo automático de IMC y estado nutricional.
   - Derivación asistencial a consultorios y tópicos de emergencia.
   - Descarga de reportes PDF de triaje asistencial.

4. **Gestión del Cuerpo Médico (`doctor.xhtml`)**:
   - Directorio oficial de médicos especialistas y acreditación profesional.
   - Módulo protegido por control de acceso basado en roles (**RBAC** - solo Administradores).
   - Exportación de nómina de doctores en PDF.

5. **Registro de Consultas Médicas (`consulta.xhtml`)**:
   - Filiación y asignación de médico tratante.
   - Juicio clínico y diagnóstico codificado (CIE-10).
   - Prescripción médica, posología farmacológica y órdenes de exámenes auxiliares.
   - Exportación de historial general de consultas a PDF.

6. **Expediente e Historial Clínico Integrado (`historial.xhtml`)**:
   - Formato oficial de Historia Clínica adaptado a estándares hospitalarios.
   - Secciones: Registro de Admisión y Triaje, Datos de Identificación, Hábitos y Factores de Riesgo, Antecedentes Personales/Familiares, y Registro Evolutivo de Consultas.
   - Descarga individualizada del expediente clínico completo en formato PDF oficial.

7. **Agenda y Calendario Asistencial (`calendario.xhtml`)**:
   - Calendario interactivo con vista estilo **Google Calendar** potenciado por `<p:schedule>`.
   - Vistas por Mes, Semana, Día y Agenda de consultorios.
   - Localización en español y sincronización en tiempo real con MySQL.

8. **Seguridad y Control de Acceso (`login.xhtml` / `SeguridadFilter.java`)**:
   - Filtro de seguridad HTTP (`WebFilter`) que protege todas las vistas.
   - Manejo de sesiones y roles (`ADMIN` y `DOCTOR`).
   - Botones de acceso rápido para demostración.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 8 / 11 / 17 / 21
* **Framework Web:** JavaServer Faces (JSF 2.2 / Mojarra)
* **Librería de Componentes:** PrimeFaces 12.0.0
* **Inyección de Dependencias:** Contexts and Dependency Injection (CDI 1.1 / Weld)
* **Base de Datos:** MySQL 5.7+ / MariaDB 10+
* **Conectividad:** JDBC (`mysql-connector-j-8.2.0.jar`)
* **Generación de Reportes:** iText PDF 2.1.7
* **Servidor Web / Servlet Container:** Apache Tomcat 8.5 / 9.0 o GlassFish 4+

---

## 🗄️ Base de Datos (`hospitalbd`)

El repositorio incluye el dump completo con datos semilla en el archivo `database.sql`:
* `usuario`: Cuentas de acceso y roles.
* `paciente`: Padrón de usuarios y datos antropométricos.
* `doctor`: Cuadro de médicos especialistas.
* `consulta`: Atenciones, diagnósticos CIE-10 y tratamientos.
* `triaje`: Controles de signos vitales y prioridad Manchester.

Para restaurar la base de datos:
```bash
mysql -u root -p < database.sql
```

---

## 🔐 Credenciales Demo

| Rol | Usuario | Contraseña | Permisos |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin` | `admin123` | Control total del sistema (Dashboard, Pacientes, Triaje, Doctores, Consultas, Historial, Calendario) |
| **Médico Tratante** | `medico` | `medico123` | Triaje, Consultas, Pacientes, Historial Clínico y Agenda |

---

## 🚀 Despliegue Local

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/jazmin-boop/HospitalJSF.git
   cd HospitalJSF
   ```
2. Importar `database.sql` en MySQL (`hospitalbd`).
3. Compilar y empaquetar con Maven o desplegar en Tomcat / GlassFish:
   ```bash
   mvn clean package
   ```
4. Copiar el archivo `target/HospitalJSF.war` en la carpeta `webapps/` de tu servidor Tomcat.
5. Acceder en el navegador a: `http://localhost:8080/HospitalJSF/`.
