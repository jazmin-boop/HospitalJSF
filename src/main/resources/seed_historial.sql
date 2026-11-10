-- Historiales Médicos Múltiples para los Pacientes de HospitalJSF
-- Cada paciente cuenta con múltiples consultas registradas en su historial clínico

USE hospitalbd;

-- Limpiar consultas previas para insertar el historial completo y ordenado
DELETE FROM consulta;

-- Consultas médicas estructuradas (Paciente 1 tiene 10 consultas para historial completo, Paciente 2 tiene 8, etc.)
INSERT INTO consulta (idConsulta, diagnostico, tratamiento, analisis, fecha, idPaciente, idDoctor, estado, hora) VALUES
-- ================= HISTORIAL PACIENTE 1 (Juan Pérez Gómez - 10 Consultas) =================
(1, 'Hipertensión Arterial Grado 1', 'Losartán 50mg c/12h por 30 días, dieta hiposódica', 'Perfil lipídico, Glucosa y Creatinina', '2026-11-01', 1, 1, 'Atendida', '09:00 AM'),
(2, 'Control Cardiológico y Monitoreo MAPA', 'Continuar Losartán 50mg, control de presión diario', 'Holter de presión arterial (MAPA)', '2026-10-20', 1, 1, 'Atendida', '10:00 AM'),
(3, 'Lumbalgia Mecánica Aguda', 'Naproxeno 550mg c/12h por 5 días, complejo B', 'Radiografía de columna lumbosacra', '2026-10-05', 1, 3, 'Atendida', '11:30 AM'),
(4, 'Gastritis Erosiva Leve', 'Esomeprazol 40mg en ayunas por 28 días', 'Test de aliento para H. Pylori', '2026-09-18', 1, 9, 'Atendida', '08:30 AM'),
(5, 'Evaluación Oftalmológica Ocupacional', 'Lentes de descanso con filtro de luz azul', 'Test de agudeza visual y tonometría', '2026-08-25', 1, 7, 'Atendida', '03:00 PM'),
(6, 'Rinofaringitis Viral Aguda', 'Paracetamol 500mg c/8h condicional, cetirizina 10mg', 'Hemograma y descarte de influenza', '2026-07-14', 1, 8, 'Atendida', '04:15 PM'),
(7, 'Control Metabólico y Chequeo Anual', 'Estilo de vida saludable, ejercicio aeróbico 30 min/día', 'Perfil bioquímico integral', '2026-06-10', 1, 10, 'Atendida', '09:45 AM'),
(8, 'Dermatitis por Contacto Alérgica', 'Mometasona crema 0.1% tópica nocturna por 5 días', 'Prueba de parche cutáneo', '2026-05-02', 1, 6, 'Atendida', '02:30 PM'),
(9, 'Cefalea Tensional Ocupacional', 'Clonixinato de lisina 125mg condicional al dolor', 'Evaluación neurológica clínica', '2026-04-12', 1, 5, 'Atendida', '11:00 AM'),
(10, 'Control Preventivo Cardiovascular', 'Aspirina preventiva 100mg diaria, control lipídico', 'Electrocardiograma basal (ECG)', '2026-11-08', 1, 1, 'En Espera', '10:30 AM'),

-- ================= HISTORIAL PACIENTE 2 (María López Sánchez - 7 Consultas) =================
(11, 'Faringoamigdalitis Aguda Bacteriana', 'Amoxicilina 500mg c/8h por 7 días, Paracetamol', 'Hemograma completo y PCR', '2026-11-02', 2, 2, 'Atendida', '10:30 AM'),
(12, 'Chequeo Ginecológico Preventivo', 'Multivitamínico con hierro y calcio', 'Papanicolau y ecografía pélvica', '2026-10-15', 2, 4, 'Atendida', '09:00 AM'),
(13, 'Rinitis Alérgica Estacional', 'Fluticasona spray nasal 2 aplicaciones c/24h', 'Panel alérgenos respiratorios', '2026-09-08', 2, 8, 'Atendida', '11:45 AM'),
(14, 'Control Dermatológico - Acné Inflamatorio', 'Gel tópico de peróxido de benzoilo 5%', 'Cultivo de secreción cutánea', '2026-08-01', 2, 6, 'Atendida', '03:15 PM'),
(15, 'Evaluación Digestiva por Dispepsia', 'Simeticona + Domperidona antes de comidas', 'Ecografía abdominal completa', '2026-06-20', 2, 9, 'Atendida', '08:00 AM'),
(16, 'Control Post-Tratamiento Amigdalitis', 'Alta médica, reposo relativo 48 horas', 'Examen clínico otorrinolaringológico', '2026-11-06', 2, 2, 'Atendida', '02:00 PM'),
(17, 'Cita Programada de Nutrición y Endocrino', 'Plan alimenticio balanceado rico en fibras', 'Perfil tiroideo TSH y T4 libre', '2026-11-09', 2, 10, 'En Espera', '11:00 AM'),

-- ================= HISTORIAL PACIENTE 3 (Ricardo Flores Ruiz - 5 Consultas) =================
(18, 'Esguince de Tobillo Derecho Grado II', 'Inmovilización con férula, Ketorolaco 10mg', 'Radiografía de tobillo AP y Lateral', '2026-11-03', 3, 3, 'Atendida', '11:15 AM'),
(19, 'Reevaluación Traumatológica y Fisioterapia', 'Terapia física 10 sesiones, vendaje neuromuscular', 'Ecografía de partes blandas', '2026-10-25', 3, 3, 'Atendida', '09:30 AM'),
(20, 'Hiperuricemia y Artritis Gotosa', 'Alopurinol 100mg diario, colchicina condicional', 'Ácido úrico en sangre y sedimento urinario', '2026-09-12', 3, 8, 'Atendida', '10:15 AM'),
(21, 'Evaluación Cardiovascular Pre-Quirúrgica', 'Riesgo quirúrgico cardiovascular Grado I', 'ECG y prueba de esfuerzo', '2026-07-28', 3, 1, 'Atendida', '04:00 PM'),
(22, 'Cita de Control Articular', 'Continuar ejercicios de fortalecimiento', 'Radiografía de control', '2026-11-10', 3, 3, 'En Espera', '09:00 AM'),

-- ================= HISTORIAL PACIENTE 4 (Rosa Huamán Díaz - 5 Consultas) =================
(23, 'Cefalea Tensional Crónica', 'Amitriptilina 25mg nocturna, Terapia de relajación', 'Resonancia Magnética Cerebral', '2026-11-04', 4, 5, 'Atendida', '02:00 PM'),
(24, 'Osteoartritis de Rodillas Bilateral', 'Glucosamina + Condroitina sobres diarios', 'Radiografía comparativa de rodillas', '2026-10-18', 4, 3, 'Atendida', '10:00 AM'),
(25, 'Control Geriátrico Integral', 'Complejo multivitamínico senior con vitamina D3', 'Densitometría ósea', '2026-09-05', 4, 8, 'Atendida', '08:45 AM'),
(26, 'Síndrome Vertiginoso Periférico', 'Betahistina 24mg cada 12 horas por 15 días', 'Audiometría y pruebas vestibulares', '2026-07-19', 4, 5, 'Atendida', '11:30 AM'),
(27, 'Consulta de Seguimiento Neurológico', 'Revisión de respuesta farmacológica', 'Test cognitivo breve', '2026-11-11', 4, 5, 'En Espera', '03:00 PM'),

-- ================= HISTORIAL PACIENTES 5 A 10 =================
(28, 'Dermatitis Atópica Severa', 'Hidrocortisona crema 1% tópica, Cetirizina 10mg', 'Biopsia por raspado cutáneo', '2026-11-05', 5, 6, 'Atendida', '03:30 PM'),
(29, 'Control Prenatal - Primer Trimestre', 'Ácido fólico 0.5mg y Sulfato Ferroso diario', 'Ecografía obstétrica morfológica', '2026-11-06', 6, 4, 'Atendida', '08:45 AM'),
(30, 'Gastritis Crónica Erosiva por H. Pylori', 'Omeprazol 20mg en ayunas, Claritromicina 500mg', 'Endoscopía Digestiva Alta con Biopsia', '2026-11-07', 7, 9, 'Atendida', '10:00 AM'),
(31, 'Diabetes Mellitus Tipo 2 no controlada', 'Metformina 850mg c/12h con las comidas', 'Hemoglobina Glicosilada (HbA1c)', '2026-11-08', 8, 10, 'En Espera', '11:30 AM'),
(32, 'Astigmatismo Miópico Compuesto bilateral', 'Prescripción de lentes correctores UV400', 'Topografía Corneal y Agudeza Visual', '2026-11-09', 9, 7, 'En Espera', '04:00 PM'),
(33, 'Síndrome Febril Agudo / Infección Urinaria', 'Ciprofloxacino 500mg c/12h por 5 días', 'Examen Completo de Orina (EGO)', '2026-11-10', 10, 8, 'En Espera', '09:30 AM'),
(34, 'Control Pediátrico y Vacunación', 'Control de crecimiento y desarrollo CRED', 'Carnet de inmunizaciones al día', '2026-11-11', 2, 2, 'En Espera', '10:00 AM'),
(35, 'Evaluación Nutricional y Perfil Tiroideo', 'Levotiroxina 50mcg en ayunas si confirma hipotiroidismo', 'TSH ultrasensible y anticuerpos TPO', '2026-11-12', 8, 10, 'En Espera', '08:30 AM');
