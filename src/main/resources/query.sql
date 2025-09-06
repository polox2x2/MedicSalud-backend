INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('MEDICO');
INSERT INTO roles (nombre) VALUES ('PACIENTE');


INSERT INTO usuarios (nombre, apellido, dni, email, clave, fechaNacimiento, telefono, direccion, fechaCreacion)
VALUES ('Carlos', 'Gonzalez', 12345678, 'carlos@gmail.com', 'clave1234', '1990-05-12', '987654321', 'Av. Lima 123', NOW());

INSERT INTO usuarios (nombre, apellido, dni, email, clave, fechaNacimiento, telefono, direccion, fechaCreacion)
VALUES ('Ana', 'Torres', 87654321, 'ana@gmail.com', 'clave5678', '1995-09-20', '912345678', 'Jr. Arequipa 456', NOW());

INSERT INTO usuarios (nombre, apellido, dni, email, clave, fechaNacimiento, telefono, direccion, fechaCreacion)
VALUES ('Luis', 'Ramirez', 45678912, 'luis@gmail.com', 'clave91011', '1985-01-15', '934567890', 'Av. Grau 789', NOW());

INSERT INTO rol_usuario (id_usuarios, id_rol) VALUES (1, 1); -- Carlos ADMIN
INSERT INTO rol_usuario (id_usuarios, id_rol) VALUES (1, 2); -- Carlos MEDICO
INSERT INTO rol_usuario (id_usuarios, id_rol) VALUES (2, 3); -- Ana PACIENTE
INSERT INTO rol_usuario (id_usuarios, id_rol) VALUES (3, 3); -- Luis PACIENTE


INSERT INTO sedes (nombreClinica, direccion) VALUES ('Clínica Central', 'Av. Universitaria 101');
INSERT INTO sedes (nombreClinica, direccion) VALUES ('Clínica Norte', 'Av. Túpac Amaru 202');


INSERT INTO medicos (nombreUsuario, especialidad, dni_usuario, id_sede)
VALUES ('Carlos Gonzalez', 'Cardiología', 12345678, 1);


INSERT INTO pacientes (nombreUsuario, dni_usuario)
VALUES ('Ana Torres', 87654321);

INSERT INTO pacientes (nombreUsuario, dni_usuario)
VALUES ('Luis Ramirez', 45678912);


INSERT INTO historiales_medicos (fechaRegistro, diagnostico, tratamiento, observaciones, id_paciente, id_medico)
VALUES (NOW(), 'Hipertensión arterial', 'Medicamentos y dieta baja en sal', 'Paciente estable', 1, 1);

INSERT INTO historiales_medicos (fechaRegistro, diagnostico, tratamiento, observaciones, id_paciente, id_medico)
VALUES (NOW(), 'Dolor de cabeza crónico', 'Paracetamol y descanso', 'Revisar en 1 semana', 2, 1);


INSERT INTO reservas (fechaCreacion, fechaCita, horaCita, estadoCita, id_medico, id_sede, id_paciente)
VALUES (NOW(), '2025-09-10', '2025-09-10 09:00:00', TRUE, 1, 1, 1);

INSERT INTO reservas (fechaCreacion, fechaCita, horaCita, estadoCita, id_medico, id_sede, id_paciente)
VALUES (NOW(), '2025-09-12', '2025-09-12 15:30:00', FALSE, 1, 2, 2);
