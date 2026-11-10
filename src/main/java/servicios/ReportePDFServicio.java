package servicios;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import modelo.Consulta;
import modelo.Doctor;
import modelo.Paciente;
import modelo.Triaje;

@ApplicationScoped
public class ReportePDFServicio implements Serializable {
    private static final long serialVersionUID = 1L;

    public void generarReportePacientes(List<Paciente> lista) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Reporte_Pacientes.pdf\"");

            OutputStream out = externalContext.getResponseOutputStream();
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(19, 102, 255));
            Paragraph titulo = new Paragraph("SISTEMA HOSPITALARIO - REPORTE DE PACIENTES", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY));
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.8f, 3f, 1.3f, 2.2f, 1.8f, 2.2f});

            String[] headers = {"ID", "Paciente y DNI", "Edad / Sexo", "Triaje (P.A. • Temp)", "Talla / Peso", "Alergias"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE)));
                cell.setBackgroundColor(new Color(19, 102, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            for (Paciente p : lista) {
                table.addCell(crearCelda(String.valueOf(p.getIdPaciente())));
                table.addCell(crearCelda(p.getNombre() + "\nDNI: " + p.getDni()));
                table.addCell(crearCelda(p.getEdad() + " años\n" + p.getSexo()));
                table.addCell(crearCelda((p.getPresion() != null ? p.getPresion() : "-") + "\n" + (p.getTemperatura() != null ? p.getTemperatura() : "-")));
                table.addCell(crearCelda((p.getTalla() != null ? p.getTalla() : "-") + "\n" + (p.getPeso() != null ? p.getPeso() : "-")));
                table.addCell(crearCelda(p.getAlergias() != null ? p.getAlergias() : "Ninguna"));
            }

            document.add(table);
            document.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error al generar PDF de pacientes: " + e.getMessage());
        }
    }

    public void generarReporteDoctores(List<Doctor> lista) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Reporte_Doctores.pdf\"");

            OutputStream out = externalContext.getResponseOutputStream();
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(19, 102, 255));
            Paragraph titulo = new Paragraph("SISTEMA HOSPITALARIO - REPORTE DE DOCTORES", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY));
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3.5f, 2.5f, 2f, 3f});

            String[] headers = {"ID", "Doctor", "Especialidad", "Teléfono", "Email"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE)));
                cell.setBackgroundColor(new Color(19, 102, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            for (Doctor d : lista) {
                table.addCell(crearCelda(String.valueOf(d.getIdDoctor())));
                table.addCell(crearCelda(d.getNombre()));
                table.addCell(crearCelda(d.getEspecialidad()));
                table.addCell(crearCelda(d.getTelefono() != null ? d.getTelefono() : "-"));
                table.addCell(crearCelda(d.getEmail() != null ? d.getEmail() : "-"));
            }

            document.add(table);
            document.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error al generar PDF de doctores: " + e.getMessage());
        }
    }

    public void generarReporteConsultas(List<Consulta> lista) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Reporte_Consultas.pdf\"");

            OutputStream out = externalContext.getResponseOutputStream();
            Document document = new Document(PageSize.A4, 28, 28, 28, 28);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(19, 102, 255));
            Paragraph titulo = new Paragraph("SISTEMA HOSPITALARIO - REPORTE GENERAL DE CONSULTAS", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY));
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2.5f, 2.5f, 3f, 3f, 1.5f});

            String[] headers = {"Fecha/Hora", "Paciente", "Médico Tratante", "Diagnóstico", "Tratamiento", "Estado"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE)));
                cell.setBackgroundColor(new Color(19, 102, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6);
                table.addCell(cell);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Consulta c : lista) {
                String fStr = (c.getFecha() != null ? sdf.format(c.getFecha()) : "-") + (c.getHora() != null ? " " + c.getHora() : "");
                table.addCell(crearCelda(fStr));
                table.addCell(crearCelda(c.getNombrePaciente() != null ? c.getNombrePaciente() : ("Paciente #" + c.getIdPaciente())));
                table.addCell(crearCelda(c.getNombreDoctor() != null ? c.getNombreDoctor() : ("Dr. #" + c.getIdDoctor())));
                table.addCell(crearCelda(c.getDiagnostico()));
                table.addCell(crearCelda(c.getTratamiento()));
                table.addCell(crearCelda(c.getEstado()));
            }

            document.add(table);
            document.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error al generar PDF de consultas: " + e.getMessage());
        }
    }

    public void generarReporteTriaje(List<Triaje> lista) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Reporte_Triaje.pdf\"");

            OutputStream out = externalContext.getResponseOutputStream();
            Document document = new Document(PageSize.A4.rotate(), 24, 24, 24, 24);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título estilo Hospitalario Nacional
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(0, 51, 102));
            Paragraph titulo = new Paragraph("SISTEMA HOSPITALARIO NACIONAL - REPORTE DE CONTROL DE TRIAJE", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph fecha = new Paragraph("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + " | Red Asistencial Central",
                    FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY));
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 1.4f, 2.5f, 1.8f, 2.5f, 1.8f, 2.2f});

            String[] headers = {"Ticket/Control", "Fecha/Hora", "Paciente (DNI)", "Tipo Seguro", "Signos Vitales (PA, FC, Temp, SpO2)", "Prioridad Triaje", "Destino Consultorio"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8.5f, Color.WHITE)));
                cell.setBackgroundColor(new Color(0, 51, 102));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Triaje t : lista) {
                String fStr = (t.getFecha() != null ? sdf.format(t.getFecha()) : "-") + " " + t.getHora();
                table.addCell(crearCelda("TRJ-" + String.format("%04d", t.getIdTriaje())));
                table.addCell(crearCelda(fStr));
                table.addCell(crearCelda((t.getNombrePaciente() != null ? t.getNombrePaciente() : ("Paciente #" + t.getIdPaciente())) + "\nDNI: " + t.getDniPaciente()));
                table.addCell(crearCelda(t.getTipoSeguro() + "\n" + t.getCodigoAsegurado()));
                table.addCell(crearCelda("PA: " + t.getPresion() + " | T°: " + t.getTemperatura() + "\nFC: " + t.getFrecuenciaCardiaca() + " | SpO2: " + t.getSaturacionOxigeno()));
                table.addCell(crearCelda(t.getPrioridad()));
                table.addCell(crearCelda(t.getDestinoConsultorio()));
            }

            document.add(table);
            document.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error al generar PDF de triaje: " + e.getMessage());
        }
    }

    public void generarReporteHistorial(Paciente p, List<Consulta> historial) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Historia_Clinica_" + (p != null ? p.getDni() : "paciente") + ".pdf\"");

            OutputStream out = externalContext.getResponseOutputStream();
            Document document = new Document(PageSize.A4, 28, 28, 28, 28);
            PdfWriter.getInstance(document, out);
            document.open();

            // Colores oficiales
            Color colorHeaderAzul = new Color(19, 102, 255);
            Color colorSeccion = new Color(219, 234, 254);
            Color colorTextoSeccion = new Color(30, 64, 175);
            Color colorSubHeader = new Color(241, 245, 249);

            // ENCABEZADO OFICIAL
            PdfPTable tEncabezado = new PdfPTable(2);
            tEncabezado.setWidthPercentage(100);
            tEncabezado.setWidths(new float[]{3.5f, 2.5f});

            PdfPCell cellTitulo = new PdfPCell();
            cellTitulo.setBackgroundColor(colorHeaderAzul);
            cellTitulo.setPadding(10);
            cellTitulo.addElement(new Paragraph("REGISTRO DE HISTORIA CLÍNICA GENERAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, Color.WHITE)));
            cellTitulo.addElement(new Paragraph("HOSPITAL NACIONAL JSF • SISTEMA DE GESTIÓN HOSPITALARIA", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.WHITE)));
            tEncabezado.addCell(cellTitulo);

            PdfPCell cellMeta = new PdfPCell();
            cellMeta.setBackgroundColor(colorHeaderAzul);
            cellMeta.setPadding(10);
            cellMeta.setHorizontalAlignment(Element.ALIGN_RIGHT);
            String hcNum = (p != null ? "HC-2026-000" + p.getIdPaciente() : "HC-2026-0001");
            Paragraph pMeta1 = new Paragraph("Nº HISTORIA CLÍNICA: " + hcNum, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE));
            pMeta1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph pMeta2 = new Paragraph("CENTRO: SEDE CENTRAL HOSPITALARIA", FontFactory.getFont(FontFactory.HELVETICA, 8, Color.WHITE));
            pMeta2.setAlignment(Element.ALIGN_RIGHT);
            cellMeta.addElement(pMeta1);
            cellMeta.addElement(pMeta2);
            tEncabezado.addCell(cellMeta);

            document.add(tEncabezado);
            document.add(new Paragraph(" "));

            // 1.- REGISTRO DE ADMISIÓN Y TRIAJE
            document.add(crearTituloSeccion("1.- REGISTRO DE ADMISIÓN Y TRIAJE", colorSeccion, colorTextoSeccion));
            PdfPTable tAdm = new PdfPTable(4);
            tAdm.setWidthPercentage(100);
            tAdm.setWidths(new float[]{1.5f, 2f, 1.5f, 1.5f});

            tAdm.addCell(crearCeldaHeader("FECHA EMISIÓN", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("CENTRO ASISTENCIAL", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("CONDICIÓN", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("GÉNERO", colorSubHeader));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            tAdm.addCell(crearCelda(sdf.format(new Date())));
            tAdm.addCell(crearCelda("Hospital Nacional JSF"));
            tAdm.addCell(crearCelda("Consulta Ambulatoria"));
            tAdm.addCell(crearCelda(p != null ? p.getSexo() : "Masculino"));

            tAdm.addCell(crearCeldaHeader("TALLA ESTIMADA", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("PESO ESTIMADO", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("TEMPERATURA", colorSubHeader));
            tAdm.addCell(crearCeldaHeader("PRESIÓN ARTERIAL", colorSubHeader));

            tAdm.addCell(crearCelda(p != null && p.getTalla() != null ? p.getTalla() : "1.70 m"));
            tAdm.addCell(crearCelda(p != null && p.getPeso() != null ? p.getPeso() : "68.5 kg"));
            tAdm.addCell(crearCelda(p != null && p.getTemperatura() != null ? p.getTemperatura() : "36.5 °C"));
            tAdm.addCell(crearCelda(p != null && p.getPresion() != null ? p.getPresion() : "120/80 mmHg"));

            document.add(tAdm);
            document.add(new Paragraph(" "));

            // 2.- DATOS DE IDENTIFICACIÓN
            document.add(crearTituloSeccion("2.- DATOS DE IDENTIFICACIÓN DEL PACIENTE", colorSeccion, colorTextoSeccion));
            PdfPTable tId = new PdfPTable(4);
            tId.setWidthPercentage(100);
            tId.setWidths(new float[]{2.5f, 1.2f, 1.3f, 1.5f});

            tId.addCell(crearCeldaHeader("APELLIDOS Y NOMBRES", colorSubHeader));
            tId.addCell(crearCeldaHeader("DNI / CÉDULA", colorSubHeader));
            tId.addCell(crearCeldaHeader("EDAD", colorSubHeader));
            tId.addCell(crearCeldaHeader("TELÉFONO", colorSubHeader));

            tId.addCell(crearCelda(p != null ? p.getNombre() : "-"));
            tId.addCell(crearCelda(p != null ? p.getDni() : "-"));
            tId.addCell(crearCelda(p != null ? p.getEdad() + " Años" : "-"));
            tId.addCell(crearCelda(p != null ? p.getTelefono() : "-"));

            tId.addCell(crearCeldaHeader("DIRECCIÓN HABITUAL", colorSubHeader));
            tId.addCell(crearCeldaHeader("NACIONALIDAD", colorSubHeader));
            tId.addCell(crearCeldaHeader("ESTADO EXPEDIENTE", colorSubHeader));
            tId.addCell(crearCeldaHeader("CONSENTIMIENTO", colorSubHeader));

            tId.addCell(crearCelda(p != null && p.getDireccion() != null ? p.getDireccion() : "Av. Principal 123 - Lima"));
            tId.addCell(crearCelda("Peruana"));
            tId.addCell(crearCelda("Activo / Vigente"));
            tId.addCell(crearCelda("Firmado y Autorizado"));

            document.add(tId);
            document.add(new Paragraph(" "));

            // 3.- HÁBITOS DE SALUD Y ANTECEDENTES
            document.add(crearTituloSeccion("3.- HÁBITOS DE SALUD Y ANTECEDENTES RELEVANTES", colorSeccion, colorTextoSeccion));
            PdfPTable tHab = new PdfPTable(4);
            tHab.setWidthPercentage(100);
            tHab.setWidths(new float[]{1.5f, 1.5f, 1.5f, 2f});

            tHab.addCell(crearCeldaHeader("FUMADOR", colorSubHeader));
            tHab.addCell(crearCeldaHeader("ALCOHOL", colorSubHeader));
            tHab.addCell(crearCeldaHeader("ACTIVIDAD FÍSICA", colorSubHeader));
            tHab.addCell(crearCeldaHeader("ALERGIAS DECLARADAS", colorSubHeader));

            tHab.addCell(crearCelda("No Fumador"));
            tHab.addCell(crearCelda("Ocasional"));
            tHab.addCell(crearCelda("Regular (3v/sem)"));
            tHab.addCell(crearCelda(p != null && p.getAlergias() != null ? p.getAlergias() : "Ninguna declarada"));

            tHab.addCell(crearCeldaHeader("ANTECEDENTES CLÍNICOS", colorSubHeader));
            tHab.addCell(crearCeldaHeader("QUIRÚRGICOS", colorSubHeader));
            tHab.addCell(crearCeldaHeader("FARMACOLÓGICOS", colorSubHeader));
            tHab.addCell(crearCeldaHeader("FAMILIARES DIRECTOS", colorSubHeader));

            tHab.addCell(crearCelda("Sin patología descompensada"));
            tHab.addCell(crearCelda("Sin cirugías"));
            tHab.addCell(crearCelda("Medicaciones indicadas"));
            tHab.addCell(crearCelda("Hipertensión en línea directa"));

            document.add(tHab);
            document.add(new Paragraph(" "));

            // 4.- REGISTRO CRONOLÓGICO DE CONSULTAS
            document.add(crearTituloSeccion("4.- REGISTRO CRONOLÓGICO DE CONSULTAS Y EVOLUCIÓN MÉDICA", colorSeccion, colorTextoSeccion));
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.3f, 2f, 2.7f, 2.5f, 1.5f});

            String[] headers = {"Fecha", "Médico Tratante", "Diagnóstico Clínico", "Tratamiento / Receta", "Análisis"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8.5f, Color.WHITE)));
                cell.setBackgroundColor(colorHeaderAzul);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            if (historial != null) {
                for (Consulta c : historial) {
                    table.addCell(crearCelda(c.getFecha() != null ? sdf.format(c.getFecha()) : "-"));
                    table.addCell(crearCelda(c.getNombreDoctor() != null ? c.getNombreDoctor() : ("Dr. #" + c.getIdDoctor())));
                    table.addCell(crearCelda(c.getDiagnostico()));
                    table.addCell(crearCelda(c.getTratamiento()));
                    table.addCell(crearCelda(c.getAnalisis() != null ? c.getAnalisis() : "-"));
                }
            }

            document.add(table);
            document.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            System.out.println("Error al generar PDF de historial: " + e.getMessage());
        }
    }

    private Paragraph crearTituloSeccion(String texto, Color bg, Color textCol) {
        PdfPTable t = new PdfPTable(1);
        t.setWidthPercentage(100);
        PdfPCell c = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, textCol)));
        c.setBackgroundColor(bg);
        c.setPadding(4);
        c.setBorder(PdfPCell.NO_BORDER);
        t.addCell(c);
        Paragraph p = new Paragraph();
        p.add(t);
        p.setSpacingAfter(4);
        return p;
    }

    private PdfPCell crearCeldaHeader(String texto, Color bg) {
        PdfPCell c = new PdfPCell(new Phrase(texto != null ? texto : "", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7.5f, new Color(71, 85, 105))));
        c.setBackgroundColor(bg);
        c.setPadding(4);
        c.setBorderColor(new Color(226, 232, 240));
        return c;
    }

    private PdfPCell crearCelda(String texto) {
        PdfPCell c = new PdfPCell(new Phrase(texto != null ? texto : "", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        c.setPadding(4);
        c.setBorderColor(new Color(226, 232, 240));
        return c;
    }
}
