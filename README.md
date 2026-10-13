# MediTrack: Aplicación de control y recordatorio de medicamentos

## 1. Descripción del proyecto

MediTrack es una aplicación móvil para dispositivos Android desarrollada con Kotlin y Jetpack Compose. Su objetivo es ayudar a los usuarios a organizar sus medicamentos, consultar las dosis y horarios registrados, recibir recordatorios y llevar un historial básico de las tomas.

La aplicación permite registrar información como el nombre del medicamento, la dosis, la unidad, la hora, la frecuencia y las fechas de inicio y finalización del tratamiento. Además, utiliza una base de datos local mediante Room para conservar la información registrada.

MediTrack busca ofrecer una herramienta sencilla y accesible para facilitar la organización de los medicamentos y ayudar a los usuarios a recordar sus horarios.

## 2. Exposición del problema

Las personas que necesitan tomar medicamentos periódicamente pueden olvidar los horarios establecidos, confundir las dosis o tener dificultades para recordar si ya realizaron una toma. Estas situaciones pueden ser más frecuentes cuando deben organizar varios medicamentos con diferentes horarios y frecuencias.

MediTrack propone una solución mediante una aplicación móvil que centraliza la información de los medicamentos, permite consultar los datos registrados y genera notificaciones para recordar los horarios programados. También contempla un historial básico que ayuda a revisar los registros de las tomas.

De esta manera, el proyecto busca facilitar la organización personal y mejorar el acceso a la información relacionada con los medicamentos registrados.

## 3. Plataforma y herramientas de desarrollo

MediTrack se desarrolla para el sistema operativo Android utilizando las siguientes herramientas y tecnologías:

- **Android Studio:** entorno de desarrollo integrado utilizado para crear, ejecutar y probar la aplicación.
- **Kotlin:** lenguaje de programación empleado para implementar la lógica de la aplicación.
- **Jetpack Compose:** herramienta utilizada para construir la interfaz de usuario.
- **Room:** biblioteca que permite almacenar y consultar los datos de los medicamentos y los registros de tomas mediante una base de datos local.
- **GitHub:** plataforma utilizada para alojar el código fuente, documentar el proyecto y llevar un registro de sus cambios.

La aplicación tiene configurada una versión mínima de Android 7.0 (API 24). Las funciones básicas de almacenamiento no requieren un servidor externo, ya que la información se guarda localmente en el dispositivo.

## 4. Interfaz de usuario e interfaz de administrador

### 4.1. Interfaz de usuario

La interfaz de usuario está diseñada para facilitar el registro y la consulta de medicamentos mediante pantallas y formularios sencillos.

Entre las funciones contempladas se encuentran:

- Registrar medicamentos con su nombre, dosis y unidad.
- Establecer la hora y la frecuencia de los recordatorios.
- Registrar las fechas de inicio y finalización del tratamiento.
- Consultar la lista de medicamentos guardados.
- Recibir notificaciones de recordatorio.
- Consultar el historial de tomas disponible.

La intención es que el usuario pueda acceder a la información principal sin tener que navegar por menús complejos.

### 4.2. Interfaz de administrador

La interfaz de administrador se mantiene como una propuesta de ampliación del proyecto. En una versión futura podría incorporar herramientas para administrar configuraciones generales o gestionar usuarios, si estas funciones llegan a ser necesarias.

La versión actual se concentra principalmente en las funciones destinadas al usuario y en el control básico de los medicamentos registrados.

## 5. Funcionalidad de la aplicación

La versión actual de MediTrack incorpora las siguientes funcionalidades:

1. **Registro de medicamentos:** permite introducir los datos principales de cada medicamento, incluidos el nombre, la dosis, la unidad, la hora, la frecuencia y las fechas del tratamiento.
2. **Lista de medicamentos:** permite consultar los medicamentos registrados y acceder a su información.
3. **Almacenamiento local:** utiliza Room para conservar los datos en la base de datos del dispositivo.
4. **Recordatorios mediante notificaciones:** programa alarmas para mostrar avisos relacionados con los horarios registrados.
5. **Historial de tomas:** dispone de una función para consultar los registros de tomas disponibles.
6. **Gestión básica de medicamentos:** incluye las acciones de administración de registros que se encuentran implementadas en la interfaz.

Las notificaciones y sus frecuencias todavía deben probarse en diferentes condiciones de uso, ya que el comportamiento de las alarmas puede verse afectado por las restricciones del sistema operativo y las configuraciones del dispositivo.

MediTrack es un proyecto académico de organización personal. No proporciona diagnósticos ni recomendaciones médicas, y sus recordatorios no sustituyen las indicaciones de un profesional de la salud.

## 6. Diseño de la aplicación

El diseño de MediTrack parte de la planificación de las pantallas y de la organización de los elementos necesarios para registrar y consultar medicamentos. Los wireframes iniciales sirvieron como referencia para definir la distribución de los formularios y la navegación de la aplicación.

Las pantallas contempladas en el proyecto son:

- **Pantalla principal:** muestra la lista de medicamentos registrados.
  
  <img width="542" height="1210" alt="Pantalla principal" src="https://github.com/user-attachments/assets/3c6623ca-a3ec-4e4b-9e2e-c0e2e4c9ef69" />

- **Formulario para agregar medicamentos:** permite introducir los datos necesarios para crear un registro.
  
  <img width="494" height="1094" alt="Formulario para agregar medicamentos" src="https://github.com/user-attachments/assets/e92c78b6-9adf-4bf3-865b-2bfecc14c40f" />

- **Lista de medicamentos:** además de mostrar los medicamentos registrados, permite realizar acciones sobre cada uno. El usuario puede marcar un medicamento como tomado, omitir una toma cuando no se realizará en ese momento o eliminar el medicamento de la lista.

  <img width="492" height="1092" alt="Lista de medicamentos" src="https://github.com/user-attachments/assets/15dd72c8-03ed-4432-996a-e6d89a4654a7" />

- **Historial de tomas:** facilita la consulta de los registros disponibles.

  <img width="492" height="1092" alt="Historial de tomas" src="https://github.com/user-attachments/assets/0bb12dff-1eef-477f-a885-63ce5f3c931d" />

- **Pantalla de administrador:** propuesta para futuras ampliaciones.

Durante el desarrollo, el diseño inicial se fue adaptando para integrar las funciones de almacenamiento y notificaciones. Se priorizó una interfaz sencilla que permitiera utilizar las funciones principales sin añadir complejidad innecesaria.

## 7. Registro de cambios (Changelog)

El siguiente registro resume la evolución de MediTrack desde su planificación inicial hasta su estado actual.

### 7.1. Cambios pasados

- Se definió la idea de MediTrack como una aplicación móvil para organizar y recordar la toma de medicamentos.
- Se identificó el problema relacionado con el olvido de horarios y la organización de las dosis.
- Se determinaron los datos principales que debía incluir cada registro de medicamento.
- Se diseñaron los wireframes iniciales para planificar las pantallas.
- Se configuró el proyecto en Android Studio con Kotlin y Jetpack Compose.
- Se estableció el paquete `com.jorge.meditrack` y la versión mínima de Android 7.0 (API 24).
- Se desarrolló la estructura inicial de la interfaz y el formulario para agregar medicamentos.
- Se incorporaron campos y selectores para introducir las dosis, unidades, horas, frecuencias y fechas.
- Se implementó la lista principal para mostrar los medicamentos registrados.
- Se creó el repositorio del proyecto en GitHub para mantener el código y la documentación.

### 7.2. Cambios actuales

- Se incorporó Room para almacenar los medicamentos de forma persistente en una base de datos local.
- Se integró el almacenamiento de los registros de tomas para el historial.
- Se implementó la programación de alarmas para generar recordatorios.
- Se incorporaron notificaciones con información sobre el medicamento y la dosis registrada.
- Se conectó la programación de los recordatorios con los datos guardados de cada medicamento.
- Se realizaron pruebas de funcionamiento y se comprobó que las notificaciones pueden recibirse en el dispositivo de prueba.
- Se revisó la integración de las frecuencias de los recordatorios.
- Se actualizó la documentación para reflejar el estado actual de la aplicación y distinguir las funciones implementadas de las mejoras futuras.

### 7.3. Cambios futuros

- Mejorar la lógica de los recordatorios para respetar las fechas de inicio y finalización de cada tratamiento.
- Comprobar y mejorar la restauración de las alarmas después de reiniciar el dispositivo.
- Ampliar las pruebas de las diferentes frecuencias y del comportamiento de las notificaciones.
- Incorporar la edición de medicamentos si se considera necesaria dentro del alcance final.
- Realizar mejoras visuales y de navegación según los resultados de las pruebas.

## 8. Estado actual del proyecto

MediTrack ha avanzado desde una propuesta inicial hasta una aplicación Android que incorpora el registro y la consulta de medicamentos, el almacenamiento local mediante Room y un sistema de notificaciones para los recordatorios.

También se ha trabajado en el historial de tomas y en la integración de los datos registrados con las alarmas programadas. Las pruebas realizadas han permitido comprobar el funcionamiento básico de las notificaciones, aunque todavía es necesario revisar algunos casos relacionados con las frecuencias y la continuidad de los recordatorios.

En esta etapa, el objetivo principal es mantener estables las funciones implementadas, comprobar su funcionamiento y completar la documentación. Las mejoras adicionales se consideran secundarias frente a la necesidad de entregar una versión funcional y comprensible del proyecto.

## 9. Conclusión

El desarrollo de MediTrack ha permitido aplicar conocimientos de programación Android, diseño de interfaces, almacenamiento local y notificaciones. A través de sus funciones principales, el proyecto busca ofrecer una herramienta sencilla para organizar medicamentos y recordar sus horarios.

La incorporación de Room permite conservar los datos registrados, mientras que las notificaciones aportan una función práctica para el uso cotidiano. Aunque existen oportunidades de mejora, la aplicación cuenta con una base funcional que puede ampliarse en futuras versiones.

El trabajo realizado también ha permitido comprender la importancia de probar las funciones, documentar los cambios y mantener un alcance realista durante el desarrollo de una aplicación móvil.
