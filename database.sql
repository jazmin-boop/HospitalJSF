-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: hospitalbd
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `hospitalbd`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `hospitalbd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `hospitalbd`;

--
-- Table structure for table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consulta` (
  `idConsulta` int(11) NOT NULL AUTO_INCREMENT,
  `diagnostico` varchar(200) DEFAULT NULL,
  `tratamiento` varchar(200) DEFAULT NULL,
  `analisis` varchar(200) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `idPaciente` int(11) DEFAULT NULL,
  `idDoctor` int(11) DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'Atendida',
  `hora` varchar(10) DEFAULT '10:00 AM',
  PRIMARY KEY (`idConsulta`),
  KEY `idPaciente` (`idPaciente`),
  KEY `idDoctor` (`idDoctor`),
  CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE CASCADE,
  CONSTRAINT `consulta_ibfk_2` FOREIGN KEY (`idDoctor`) REFERENCES `doctor` (`idDoctor`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consulta`
--

LOCK TABLES `consulta` WRITE;
/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
INSERT INTO `consulta` VALUES (1,'Hipertensión Arterial Grado 1','Losartán 50mg c/12h por 30 días, dieta hiposódica','Perfil lipídico, Glucosa y Creatinina','2026-11-01',1,1,'Atendida','09:00 AM'),(2,'Control Cardiológico y Monitoreo MAPA','Continuar Losartán 50mg, control de presión diario','Holter de presión arterial (MAPA)','2026-10-20',1,1,'Atendida','10:00 AM'),(3,'Lumbalgia Mecánica Aguda','Naproxeno 550mg c/12h por 5 días, complejo B','Radiografía de columna lumbosacra','2026-10-05',1,3,'Atendida','11:30 AM'),(4,'Gastritis Erosiva Leve','Esomeprazol 40mg en ayunas por 28 días','Test de aliento para H. Pylori','2026-09-18',1,9,'Atendida','08:30 AM'),(5,'Evaluación Oftalmológica Ocupacional','Lentes de descanso con filtro de luz azul','Test de agudeza visual y tonometría','2026-08-25',1,7,'Atendida','03:00 PM'),(6,'Rinofaringitis Viral Aguda','Paracetamol 500mg c/8h condicional, cetirizina 10mg','Hemograma y descarte de influenza','2026-07-14',1,8,'Atendida','04:15 PM'),(7,'Control Metabólico y Chequeo Anual','Estilo de vida saludable, ejercicio aeróbico 30 min/día','Perfil bioquímico integral','2026-06-10',1,10,'Atendida','09:45 AM'),(8,'Dermatitis por Contacto Alérgica','Mometasona crema 0.1% tópica nocturna por 5 días','Prueba de parche cutáneo','2026-05-02',1,6,'Atendida','02:30 PM'),(9,'Cefalea Tensional Ocupacional','Clonixinato de lisina 125mg condicional al dolor','Evaluación neurológica clínica','2026-04-12',1,5,'Atendida','11:00 AM'),(10,'Control Preventivo Cardiovascular','Aspirina preventiva 100mg diaria, control lipídico','Electrocardiograma basal (ECG)','2026-11-08',1,1,'En Espera','10:30 AM'),(11,'Faringoamigdalitis Aguda Bacteriana','Amoxicilina 500mg c/8h por 7 días, Paracetamol','Hemograma completo y PCR','2026-11-02',2,2,'Atendida','10:30 AM'),(12,'Chequeo Ginecológico Preventivo','Multivitamínico con hierro y calcio','Papanicolau y ecografía pélvica','2026-10-15',2,4,'Atendida','09:00 AM'),(13,'Rinitis Alérgica Estacional','Fluticasona spray nasal 2 aplicaciones c/24h','Panel alérgenos respiratorios','2026-09-08',2,8,'Atendida','11:45 AM'),(14,'Control Dermatológico - Acné Inflamatorio','Gel tópico de peróxido de benzoilo 5%','Cultivo de secreción cutánea','2026-08-01',2,6,'Atendida','03:15 PM'),(15,'Evaluación Digestiva por Dispepsia','Simeticona + Domperidona antes de comidas','Ecografía abdominal completa','2026-06-20',2,9,'Atendida','08:00 AM'),(16,'Control Post-Tratamiento Amigdalitis','Alta médica, reposo relativo 48 horas','Examen clínico otorrinolaringológico','2026-11-06',2,2,'Atendida','02:00 PM'),(17,'Cita Programada de Nutrición y Endocrino','Plan alimenticio balanceado rico en fibras','Perfil tiroideo TSH y T4 libre','2026-11-09',2,10,'En Espera','11:00 AM'),(18,'Esguince de Tobillo Derecho Grado II','Inmovilización con férula, Ketorolaco 10mg','Radiografía de tobillo AP y Lateral','2026-11-03',3,3,'Atendida','11:15 AM'),(19,'Reevaluación Traumatológica y Fisioterapia','Terapia física 10 sesiones, vendaje neuromuscular','Ecografía de partes blandas','2026-10-25',3,3,'Atendida','09:30 AM'),(20,'Hiperuricemia y Artritis Gotosa','Alopurinol 100mg diario, colchicina condicional','Ácido úrico en sangre y sedimento urinario','2026-09-12',3,8,'Atendida','10:15 AM'),(21,'Evaluación Cardiovascular Pre-Quirúrgica','Riesgo quirúrgico cardiovascular Grado I','ECG y prueba de esfuerzo','2026-07-28',3,1,'Atendida','04:00 PM'),(22,'Cita de Control Articular','Continuar ejercicios de fortalecimiento','Radiografía de control','2026-11-10',3,3,'En Espera','09:00 AM'),(23,'Cefalea Tensional Crónica','Amitriptilina 25mg nocturna, Terapia de relajación','Resonancia Magnética Cerebral','2026-11-04',4,5,'Atendida','02:00 PM'),(24,'Osteoartritis de Rodillas Bilateral','Glucosamina + Condroitina sobres diarios','Radiografía comparativa de rodillas','2026-10-18',4,3,'Atendida','10:00 AM'),(25,'Control Geriátrico Integral','Complejo multivitamínico senior con vitamina D3','Densitometría ósea','2026-09-05',4,8,'Atendida','08:45 AM'),(26,'Síndrome Vertiginoso Periférico','Betahistina 24mg cada 12 horas por 15 días','Audiometría y pruebas vestibulares','2026-07-19',4,5,'Atendida','11:30 AM'),(27,'Consulta de Seguimiento Neurológico','Revisión de respuesta farmacológica','Test cognitivo breve','2026-11-11',4,5,'En Espera','03:00 PM'),(28,'Dermatitis Atópica Severa','Hidrocortisona crema 1% tópica, Cetirizina 10mg','Biopsia por raspado cutáneo','2026-11-05',5,6,'Atendida','03:30 PM'),(29,'Control Prenatal - Primer Trimestre','Ácido fólico 0.5mg y Sulfato Ferroso diario','Ecografía obstétrica morfológica','2026-11-06',6,4,'Atendida','08:45 AM'),(30,'Gastritis Crónica Erosiva por H. Pylori','Omeprazol 20mg en ayunas, Claritromicina 500mg','Endoscopía Digestiva Alta con Biopsia','2026-11-07',7,9,'Atendida','10:00 AM'),(31,'Diabetes Mellitus Tipo 2 no controlada','Metformina 850mg c/12h con las comidas','Hemoglobina Glicosilada (HbA1c)','2026-11-08',8,10,'En Espera','11:30 AM'),(32,'Astigmatismo Miópico Compuesto bilateral','Prescripción de lentes correctores UV400','Topografía Corneal y Agudeza Visual','2026-11-09',9,7,'En Espera','04:00 PM'),(33,'Síndrome Febril Agudo / Infección Urinaria','Ciprofloxacino 500mg c/12h por 5 días','Examen Completo de Orina (EGO)','2026-11-10',10,8,'En Espera','09:30 AM'),(34,'Control Pediátrico y Vacunación','Control de crecimiento y desarrollo CRED','Carnet de inmunizaciones al día','2026-11-11',2,2,'En Espera','10:00 AM'),(35,'Evaluación Nutricional y Perfil Tiroideo','Levotiroxina 50mcg en ayunas si confirma hipotiroidismo','TSH ultrasensible y anticuerpos TPO','2026-11-12',8,10,'En Espera','08:30 AM');
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `idDoctor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `especialidad` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idDoctor`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Dr. Carlos Pérez','Cardiología','987111222','carlos.perez@hospital.pe'),(2,'Dra. Ana Torres','Pediatría','987333444','ana.torres@hospital.pe'),(3,'Dr. Luis Ramos','Traumatología','987555666','luis.ramos@hospital.pe'),(4,'Dra. Carmen Mendoza','Ginecología','987777888','carmen.mendoza@hospital.pe'),(5,'Dr. Roberto Silva','Neurología','987999000','roberto.silva@hospital.pe'),(6,'Dra. Elena Castro','Dermatología','986111333','elena.castro@hospital.pe'),(7,'Dr. Jorge Morales','Oftalmología','986222444','jorge.morales@hospital.pe'),(8,'Dra. Patricia Quispe','Medicina General','986333555','patricia.quispe@hospital.pe'),(9,'Dr. Manuel Herrera','Gastroenterología','986444666','manuel.herrera@hospital.pe'),(10,'Dra. Sofía Vargas','Endocrinología','986555777','sofia.vargas@hospital.pe');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paciente` (
  `idPaciente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `dni` varchar(8) DEFAULT NULL,
  `sexo` varchar(20) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `talla` varchar(10) DEFAULT NULL,
  `peso` varchar(10) DEFAULT NULL,
  `presion` varchar(20) DEFAULT NULL,
  `temperatura` varchar(10) DEFAULT NULL,
  `alergias` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPaciente`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (1,'Juan Pérez Gómez',35,'12345678','Masculino','987654321','Av. Los Próceres 452 - San Miguel','1.70 m','68.5 kg','120/80 mmHg','36.5 °C','Ninguna conocida'),(2,'María López Sánchez',28,'23456789','Femenino','912345678','Jr. Huancavelica 780 - Cercado','1.62 m','54.0 kg','110/70 mmHg','36.6 °C','Alergia a Penicilina'),(3,'Ricardo Flores Ruiz',45,'34567890','Masculino','923456789','Av. Javier Prado Este 2140 - San Borja','1.75 m','82.0 kg','130/85 mmHg','36.8 °C','Alergia a Sulfas'),(4,'Rosa Huamán Díaz',62,'45678901','Femenino','934567890','Ca. Los Robles 112 - Surco','1.55 m','60.5 kg','125/80 mmHg','36.4 °C','Ninguna conocida'),(5,'Carlos Gutiérrez Prado',19,'56789012','Masculino','945678901','Av. Universitaria 1450 - SMP','1.78 m','71.0 kg','115/75 mmHg','36.7 °C','Ninguna conocida'),(6,'Lucía Benítez Romero',31,'67890123','Femenino','956789012','Av. Arequipa 3210 - Miraflores','1.65 m','58.0 kg','118/78 mmHg','36.5 °C','Ninguna conocida'),(7,'Fernando Morales Vega',54,'78901234','Masculino','967890123','Ca. Manuel Bonilla 240 - Miraflores','1.68 m','76.5 kg','135/85 mmHg','36.9 °C','AINEs / Ibuprofeno'),(8,'Gabriela Salazar Castro',40,'89012345','Femenino','978901234','Av. La Marina 1890 - Pueblo Libre','1.60 m','64.0 kg','120/80 mmHg','36.6 °C','Ninguna conocida'),(9,'Javier Paredes Ortiz',26,'90123456','Masculino','989012345','Jr. Las Flores 341 - Jesús María','1.74 m','69.0 kg','122/82 mmHg','36.5 °C','Ninguna conocida'),(10,'Andrea Villavicencio Meza',37,'81234567','Femenino','990123456','Ca. Las Palmeras 560 - Magdalena','1.63 m','56.5 kg','110/70 mmHg','36.4 °C','Ninguna conocida');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `triaje`
--

DROP TABLE IF EXISTS `triaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `triaje` (
  `idTriaje` int(11) NOT NULL AUTO_INCREMENT,
  `idPaciente` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` varchar(10) NOT NULL,
  `presion` varchar(20) DEFAULT '120/80 mmHg',
  `temperatura` varchar(10) DEFAULT '36.5 °C',
  `frecuenciaCardiaca` varchar(10) DEFAULT '72 lpm',
  `frecuenciaRespiratoria` varchar(10) DEFAULT '18 rpm',
  `saturacionOxigeno` varchar(10) DEFAULT '98 %',
  `peso` varchar(10) DEFAULT '68.5 kg',
  `talla` varchar(10) DEFAULT '1.70 m',
  `imc` varchar(10) DEFAULT '23.7',
  `estadoNutricional` varchar(50) DEFAULT 'Normal',
  `prioridad` varchar(40) DEFAULT 'Prioridad III (Urgencia Menor)',
  `tipoSeguro` varchar(50) DEFAULT 'Seguro Integral',
  `codigoAsegurado` varchar(30) DEFAULT '01-004-12345678',
  `motivoConsulta` varchar(255) DEFAULT 'Control de rutina / Evaluación médica',
  `destinoConsultorio` varchar(100) DEFAULT 'Consultorio 1 - Cardiología',
  `estado` varchar(30) DEFAULT 'Triado',
  PRIMARY KEY (`idTriaje`),
  KEY `idPaciente` (`idPaciente`),
  CONSTRAINT `triaje_ibfk_1` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `triaje`
--

LOCK TABLES `triaje` WRITE;
/*!40000 ALTER TABLE `triaje` DISABLE KEYS */;
INSERT INTO `triaje` VALUES (1,1,'2026-11-01','08:45 AM','120/80 mmHg','36.5 °C','72 lpm','18 rpm','98 %','68.5 kg','1.70 m','23.7','Normal','Prioridad III (Urgencia Menor)','Seguro Integral','01-004-12345678','Control de presión arterial elevada y cefalea leve','Consultorio 1 - Cardiología','Atendido'),(2,2,'2026-11-02','10:15 AM','110/70 mmHg','36.6 °C','80 lpm','20 rpm','99 %','54.0 kg','1.62 m','20.6','Normal','Prioridad III (Urgencia Menor)','Seguro Regular','01-008-23456789','Dolor de garganta agudo, malestar y fiebre cuantificada','Consultorio 2 - Pediatría','Atendido'),(3,3,'2026-11-03','10:50 AM','130/85 mmHg','36.8 °C','88 lpm','20 rpm','97 %','82.0 kg','1.75 m','26.8','Sobrepeso','Prioridad II (Urgencia Mayor)','Seguro Particular','02-015-34567890','Traumatismo en tobillo con edema evidente e incapacidad de apoyo','Consultorio 3 - Traumatología','Atendido'),(4,4,'2026-11-04','01:30 PM','125/80 mmHg','36.4 °C','68 lpm','16 rpm','98 %','60.5 kg','1.55 m','25.2','Sobrepeso','Prioridad III (Urgencia Menor)','Seguro Integral','01-004-45678901','Dolor de cabeza holocraneal opresivo de varios días de evolución','Consultorio 5 - Neurología','Atendido'),(5,5,'2026-11-05','03:10 PM','115/75 mmHg','36.7 °C','75 lpm','18 rpm','99 %','71.0 kg','1.78 m','22.4','Normal','Prioridad IV (No Urgente)','Seguro Regular','01-004-56789012','Lesiones eritematosas pruriginosas en miembros superiores','Consultorio 6 - Dermatología','Atendido'),(6,6,'2026-11-06','08:20 AM','118/78 mmHg','36.5 °C','76 lpm','18 rpm','99 %','58.0 kg','1.65 m','21.3','Normal','Prioridad IV (No Urgente)','Seguro Integral','01-004-67890123','Control prenatal primer trimestre sin sangrado ni dolor','Consultorio 4 - Ginecología','Atendido'),(7,7,'2026-11-07','09:40 AM','135/85 mmHg','36.9 °C','84 lpm','19 rpm','97 %','76.5 kg','1.68 m','27.1','Sobrepeso','Prioridad II (Urgencia Mayor)','Seguro Particular','02-005-78901234','Epigastralgia intensa tipo ardor postprandial y náuseas','Consultorio 9 - Gastroenterología','En Espera'),(8,8,'2026-11-08','11:00 AM','120/80 mmHg','36.6 °C','74 lpm','18 rpm','98 %','64.0 kg','1.60 m','25.0','Sobrepeso','Prioridad III (Urgencia Menor)','Seguro Regular','01-004-89012345','Polidipsia, poliuria y control glucémico ambulatorio','Consultorio 10 - Endocrinología','En Espera'),(9,9,'2026-11-09','03:40 PM','122/82 mmHg','36.5 °C','70 lpm','17 rpm','98 %','69.0 kg','1.74 m','22.8','Normal','Prioridad IV (No Urgente)','Seguro Integral','01-004-90123456','Disminución progresiva de agudeza visual para lectura','Consultorio 7 - Oftalmología','En Espera'),(10,10,'2026-11-10','09:10 AM','110/70 mmHg','37.8 °C','92 lpm','22 rpm','97 %','56.5 kg','1.63 m','21.3','Normal','Prioridad II (Urgencia Mayor)','Seguro Integral','01-004-81234567','Disuria intensa, polaquiuria y alza térmica no cuantificada','Consultorio 8 - Medicina General','En Espera');
/*!40000 ALTER TABLE `triaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `rol` varchar(50) DEFAULT NULL,
  `idDoctor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','admin123','Dr. Carlos Pérez','ADMIN',1),(2,'medico','medico123','Dra. Ana Torres','DOCTOR',2),(3,'lramos','doctor123','Dr. Luis Ramos','DOCTOR',3),(4,'cmendoza','doctor123','Dra. Carmen Mendoza','DOCTOR',4),(5,'rsilva','doctor123','Dr. Roberto Silva','DOCTOR',5),(6,'ecastro','doctor123','Dra. Elena Castro','DOCTOR',6),(7,'jmorales','doctor123','Dr. Jorge Morales','DOCTOR',7),(8,'pquispe','doctor123','Dra. Patricia Quispe','DOCTOR',8),(9,'mherrera','doctor123','Dr. Manuel Herrera','DOCTOR',9),(10,'svargas','doctor123','Dra. Sofía Vargas','DOCTOR',10);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-09 19:55:15
