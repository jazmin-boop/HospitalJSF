-- Script de Datos Semilla para HospitalJSF
-- Base de datos: hospitalbd

USE hospitalbd;

-- Asegurar columnas en tabla doctor
ALTER TABLE doctor ADD COLUMN IF NOT EXISTS telefono VARCHAR(20) AFTER especialidad;
ALTER TABLE doctor ADD COLUMN IF NOT EXISTS email VARCHAR(100) AFTER telefono;

-- 1. DOCTORES (10 registros)
INSERT INTO doctor (idDoctor, nombre, especialidad, telefono, email) VALUES
(1, 'Dr. Carlos Pérez', 'Cardiología', '987111222', 'carlos.perez@hospital.pe'),
(2, 'Dra. Ana Torres', 'Pediatría', '987333444', 'ana.torres@hospital.pe'),
(3, 'Dr. Luis Ramos', 'Traumatología', '987555666', 'luis.ramos@hospital.pe'),
(4, 'Dra. Carmen Mendoza', 'Ginecología', '987777888', 'carmen.mendoza@hospital.pe'),
(5, 'Dr. Roberto Silva', 'Neurología', '987999000', 'roberto.silva@hospital.pe'),
(6, 'Dra. Elena Castro', 'Dermatología', '986111333', 'elena.castro@hospital.pe'),
(7, 'Dr. Jorge Morales', 'Oftalmología', '986222444', 'jorge.morales@hospital.pe'),
(8, 'Dra. Patricia Quispe', 'Medicina General', '986333555', 'patricia.quispe@hospital.pe'),
(9, 'Dr. Manuel Herrera', 'Gastroenterología', '986444666', 'manuel.herrera@hospital.pe'),
(10, 'Dra. Sofía Vargas', 'Endocrinología', '986555777', 'sofia.vargas@hospital.pe')
ON DUPLICATE KEY UPDATE 
    nombre=VALUES(nombre), 
    especialidad=VALUES(especialidad), 
    telefono=VALUES(telefono), 
    email=VALUES(email);

-- 2. PACIENTES (10 registros)
INSERT INTO paciente (idPaciente, nombre, edad, dni, sexo, telefono) VALUES
(1, 'Juan Pérez Gómez', 35, '12345678', 'Masculino', '987654321'),
(2, 'María López Sánchez', 28, '23456789', 'Femenino', '912345678'),
(3, 'Ricardo Flores Ruiz', 45, '34567890', 'Masculino', '923456789'),
(4, 'Rosa Huamán Díaz', 62, '45678901', 'Femenino', '934567890'),
(5, 'Carlos Gutiérrez Prado', 19, '56789012', 'Masculino', '945678901'),
(6, 'Lucía Benítez Romero', 31, '67890123', 'Femenino', '956789012'),
(7, 'Fernando Morales Vega', 54, '78901234', 'Masculino', '967890123'),
(8, 'Gabriela Salazar Castro', 40, '89012345', 'Femenino', '978901234'),
(9, 'Javier Paredes Ortiz', 26, '90123456', 'Masculino', '989012345'),
(10, 'Andrea Villavicencio Meza', 37, '81234567', 'Femenino', '990123456')
ON DUPLICATE KEY UPDATE
    nombre=VALUES(nombre),
    edad=VALUES(edad),
    dni=VALUES(dni),
    sexo=VALUES(sexo),
    telefono=VALUES(telefono);

-- 3. CONSULTAS MÉDICAS (10 registros)
INSERT INTO consulta (idConsulta, diagnostico, tratamiento, analisis, fecha, idPaciente, idDoctor, estado, hora) VALUES
(1, 'Hipertensión Arterial Grado 1', 'Losartán 50mg cada 12h por 30 días', 'Perfil lipídico, Glucosa y Creatinina', '2026-11-01', 1, 1, 'Atendida', '09:00 AM'),
(2, 'Faringoamigdalitis Aguda Bacteriana', 'Amoxicilina 500mg cada 8h por 7 días, Paracetamol 500mg', 'Hemograma completo y PCR', '2026-11-02', 2, 2, 'Atendida', '10:30 AM'),
(3, 'Esguince de Tobillo Derecho Grado II', 'Inmovilización con férula, Ketorolaco 10mg condicional', 'Radiografía de tobillo AP y Lateral', '2026-11-03', 3, 3, 'En Espera', '11:15 AM'),
(4, 'Cefalea Tensional Crónica', 'Amitriptilina 25mg nocturna, Terapia de relajación', 'Resonancia Magnética Cerebral', '2026-11-04', 4, 5, 'Atendida', '02:00 PM'),
(5, 'Dermatitis Atópica Severa', 'Hidrocortisona crema 1% tópica c/12h por 5 días, Cetirizina 10mg', 'Biopsia por raspado cutáneo', '2026-11-05', 5, 6, 'Atendida', '03:30 PM'),
(6, 'Control Prenatal - Primer Trimestre', 'Ácido fólico 0.5mg y Sulfato Ferroso diario', 'Ecografía obstétrica morfológica', '2026-11-06', 6, 4, 'Atendida', '08:45 AM'),
(7, 'Gastritis Crónica Erosiva por H. Pylori', 'Omeprazol 20mg en ayunas, Claritromicina + Amoxicilina 14 días', 'Endoscopía Digestiva Alta con Biopsia', '2026-11-07', 7, 9, 'En Espera', '10:00 AM'),
(8, 'Diabetes Mellitus Tipo 2', 'Metformina 850mg con almuerzo y cena, Dieta baja en carbohidratos', 'Hemoglobina Glicosilada (HbA1c)', '2026-11-08', 8, 10, 'En Espera', '11:30 AM'),
(9, 'Astigmatismo Miópico Compuesto', 'Prescripción de lentes correctores oftálmicos con filtro UV', 'Topografía Corneal y Agudeza Visual', '2026-11-09', 9, 7, 'En Espera', '04:00 PM'),
(10, 'Infección Urinaria Aguda', 'Ciprofloxacino 500mg cada 12h por 5 días, hidratación abundante', 'Examen Completo de Orina (EGO) y Urocultivo', '2026-11-10', 10, 8, 'En Espera', '09:30 AM')
ON DUPLICATE KEY UPDATE
    diagnostico=VALUES(diagnostico),
    tratamiento=VALUES(tratamiento),
    analisis=VALUES(analisis),
    fecha=VALUES(fecha),
    idPaciente=VALUES(idPaciente),
    idDoctor=VALUES(idDoctor),
    estado=VALUES(estado),
    hora=VALUES(hora);

-- 4. USUARIOS (10 registros)
INSERT INTO usuario (idUsuario, username, password, nombre, rol, idDoctor) VALUES
(1, 'admin', 'admin123', 'Dr. Carlos Pérez', 'ADMIN', 1),
(2, 'medico', 'medico123', 'Dra. Ana Torres', 'DOCTOR', 2),
(3, 'lramos', 'doctor123', 'Dr. Luis Ramos', 'DOCTOR', 3),
(4, 'cmendoza', 'doctor123', 'Dra. Carmen Mendoza', 'DOCTOR', 4),
(5, 'rsilva', 'doctor123', 'Dr. Roberto Silva', 'DOCTOR', 5),
(6, 'ecastro', 'doctor123', 'Dra. Elena Castro', 'DOCTOR', 6),
(7, 'jmorales', 'doctor123', 'Dr. Jorge Morales', 'DOCTOR', 7),
(8, 'pquispe', 'doctor123', 'Dra. Patricia Quispe', 'DOCTOR', 8),
(9, 'mherrera', 'doctor123', 'Dr. Manuel Herrera', 'DOCTOR', 9),
(10, 'svargas', 'doctor123', 'Dra. Sofía Vargas', 'DOCTOR', 10)
ON DUPLICATE KEY UPDATE
    username=VALUES(username),
    password=VALUES(password),
    nombre=VALUES(nombre),
    rol=VALUES(rol),
    idDoctor=VALUES(idDoctor);
