/* DELETE 'proyectoCiudadesDB' database*/
DROP SCHEMA IF EXISTS proyectoCiudadesDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'%';



/* CREATE 'proyectoCiudadesDB' DATABASE */
CREATE SCHEMA proyectoCiudadesDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';
/* GRANT FULL ACCESS TO THE DATABASE FOR THE USER 'spq' AT LOCAL SERVER*/
GRANT ALL ON proyectoCiudadesDB.* TO 'spq'@'%';

