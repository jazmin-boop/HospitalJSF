USE hospitalbd;

CREATE TABLE IF NOT EXISTS triaje (
    idTriaje INT AUTO_INCREMENT PRIMARY KEY,
    idPaciente INT NOT NULL,
    fecha DATE NOT NULL,
    hora VARCHAR(10) NOT NULL,
    presion VARCHAR(20) DEFAULT '120/80 mmHg',
    temperatura VARCHAR(10) DEFAULT '36.5 °C',
    frecuenciaCardiaca VARCHAR(10) DEFAULT '72 lpm',
    frecuenciaRespiratoria VARCHAR(10) DEFAULT '18 rpm',
    saturacionOxigeno VARCHAR(10) DEFAULT '98 %',
    peso VARCHAR(10) DEFAULT '68.5 kg',
    talla VARCHAR(10) DEFAULT '1.70 m',
    imc VARCHAR(10) DEFAULT '23.7',
    estadoNutricional VARCHAR(50) DEFAULT 'Normal',
    prioridad VARCHAR(40) DEFAULT 'Prioridad III (Urgencia Menor)',
    tipoSeguro VARCHAR(50) DEFAULT 'Seguro Integral',
    codigoAsegurado VARCHAR(30) DEFAULT '01-004-12345678',
    motivoConsulta VARCHAR(255) DEFAULT 'Control de rutina / Evaluación médica',
    destinoConsultorio VARCHAR(100) DEFAULT 'Consultorio 1 - Cardiología',
    estado VARCHAR(30) DEFAULT 'Triado',
    FOREIGN KEY (idPaciente) REFERENCES paciente(idPaciente) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Sembrar 10 registros de triaje institucional para los 10 pacientes
DELETE FROM triaje;

INSERT INTO triaje (idTriaje, idPaciente, fecha, hora, presion, temperatura, frecuenciaCardiaca, frecuenciaRespiratoria, saturacionOxigeno, peso, talla, imc, estadoNutricional, prioridad, tipoSeguro, codigoAsegurado, motivoConsulta, destinoConsultorio, estado) VALUES
(1, 1, '2026-11-01', '08:45 AM', '120/80 mmHg', '36.5 °C', '72 lpm', '18 rpm', '98 %', '68.5 kg', '1.70 m', '23.7', 'Normal', 'Prioridad III (Urgencia Menor)', 'Seguro Integral', '01-004-12345678', 'Control de presión arterial elevada y cefalea leve', 'Consultorio 1 - Cardiología', 'Atendido'),
(2, 2, '2026-11-02', '10:15 AM', '110/70 mmHg', '36.6 °C', '80 lpm', '20 rpm', '99 %', '54.0 kg', '1.62 m', '20.6', 'Normal', 'Prioridad III (Urgencia Menor)', 'Seguro Regular', '01-008-23456789', 'Dolor de garganta agudo, malestar y fiebre cuantificada', 'Consultorio 2 - Pediatría', 'Atendido'),
(3, 3, '2026-11-03', '10:50 AM', '130/85 mmHg', '36.8 °C', '88 lpm', '20 rpm', '97 %', '82.0 kg', '1.75 m', '26.8', 'Sobrepeso', 'Prioridad II (Urgencia Mayor)', 'Seguro Particular', '02-015-34567890', 'Traumatismo en tobillo con edema evidente e incapacidad de apoyo', 'Consultorio 3 - Traumatología', 'Atendido'),
(4, 4, '2026-11-04', '01:30 PM', '125/80 mmHg', '36.4 °C', '68 lpm', '16 rpm', '98 %', '60.5 kg', '1.55 m', '25.2', 'Sobrepeso', 'Prioridad III (Urgencia Menor)', 'Seguro Integral', '01-004-45678901', 'Dolor de cabeza holocraneal opresivo de varios días de evolución', 'Consultorio 5 - Neurología', 'Atendido'),
(5, 5, '2026-11-05', '03:10 PM', '115/75 mmHg', '36.7 °C', '75 lpm', '18 rpm', '99 %', '71.0 kg', '1.78 m', '22.4', 'Normal', 'Prioridad IV (No Urgente)', 'Seguro Regular', '01-004-56789012', 'Lesiones eritematosas pruriginosas en miembros superiores', 'Consultorio 6 - Dermatología', 'Atendido'),
(6, 6, '2026-11-06', '08:20 AM', '118/78 mmHg', '36.5 °C', '76 lpm', '18 rpm', '99 %', '58.0 kg', '1.65 m', '21.3', 'Normal', 'Prioridad IV (No Urgente)', 'Seguro Integral', '01-004-67890123', 'Control prenatal primer trimestre sin sangrado ni dolor', 'Consultorio 4 - Ginecología', 'Atendido'),
(7, 7, '2026-11-07', '09:40 AM', '135/85 mmHg', '36.9 °C', '84 lpm', '19 rpm', '97 %', '76.5 kg', '1.68 m', '27.1', 'Sobrepeso', 'Prioridad II (Urgencia Mayor)', 'Seguro Particular', '02-005-78901234', 'Epigastralgia intensa tipo ardor postprandial y náuseas', 'Consultorio 9 - Gastroenterología', 'En Espera'),
(8, 8, '2026-11-08', '11:00 AM', '120/80 mmHg', '36.6 °C', '74 lpm', '18 rpm', '98 %', '64.0 kg', '1.60 m', '25.0', 'Sobrepeso', 'Prioridad III (Urgencia Menor)', 'Seguro Regular', '01-004-89012345', 'Polidipsia, poliuria y control glucémico ambulatorio', 'Consultorio 10 - Endocrinología', 'En Espera'),
(9, 9, '2026-11-09', '03:40 PM', '122/82 mmHg', '36.5 °C', '70 lpm', '17 rpm', '98 %', '69.0 kg', '1.74 m', '22.8', 'Normal', 'Prioridad IV (No Urgente)', 'Seguro Integral', '01-004-90123456', 'Disminución progresiva de agudeza visual para lectura', 'Consultorio 7 - Oftalmología', 'En Espera'),
(10, 10, '2026-11-10', '09:10 AM', '110/70 mmHg', '37.8 °C', '92 lpm', '22 rpm', '97 %', '56.5 kg', '1.63 m', '21.3', 'Normal', 'Prioridad II (Urgencia Mayor)', 'Seguro Integral', '01-004-81234567', 'Disuria intensa, polaquiuria y alza térmica no cuantificada', 'Consultorio 8 - Medicina General', 'En Espera');
