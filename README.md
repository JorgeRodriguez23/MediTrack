# Aplicación de control y recordatorio de medicamentos

# Descripción del proyecto

El proyecto consiste en desarrollar una aplicación móvil que permita a los usuarios registrar y llevar un control básico de los medicamentos que deben tomar. La aplicación tendrá como objetivo principal ayudar a organizar los horarios y las dosis de los medicamentos, permitiendo consultar fácilmente la información y registrar si una dosis ya fue tomada o si quedó pendiente. El usuario podrá registrar información como el nombre del medicamento, la cantidad o dosis, la hora en que debe tomarlo, la frecuencia con la que debe hacerlo, la fecha de inicio y finalización, y algunas notas adicionales. De esta manera, toda la información relacionada con sus medicamentos podrá mantenerse organizada dentro de una misma aplicación. La aplicación estará enfocada en el recordatorio y registro de medicamentos, por lo que no tendrá como finalidad realizar diagnósticos, recomendar tratamientos ni sustituir las indicaciones proporcionadas por un profesional de la salud.

# Exposición del problema

Las personas que deben tomar medicamentos de manera periódica pueden tener dificultades para recordar los horarios, las dosis o si ya realizaron una determinada toma. Esta situación puede ser especialmente complicada cuando una persona utiliza varios medicamentos con diferentes horarios y frecuencias. Llevar esta información únicamente en la memoria, en notas escritas o en diferentes aplicaciones puede provocar confusión y dificultar el seguimiento de las tomas. Por esta razón, se propone desarrollar una aplicación que permita centralizar esta información y facilitar el seguimiento de los medicamentos. El usuario podrá consultar sus medicamentos registrados y conocer qué dosis debe tomar y en qué momento, además de registrar cada toma realizada. El propósito de la aplicación no será indicar al usuario qué medicamento debe utilizar, sino proporcionar una herramienta de organización y seguimiento basada en las indicaciones que ya haya recibido.

# Plataforma
   
La aplicación será diseñada principalmente para dispositivos móviles con sistema operativo Android. Para el desarrollo se considerará el uso de herramientas de desarrollo para aplicaciones móviles, seleccionando aquellas que permitan implementar de manera sencilla las funciones necesarias para el proyecto. Durante la primera etapa se priorizarán las funciones básicas y una interfaz sencilla, de manera que el proyecto pueda desarrollarse progresivamente. Posteriormente se podrán incorporar funciones adicionales como notificaciones, almacenamiento persistente e historial de las tomas. El código y los avances del proyecto serán administrados mediante GitHub, utilizando un repositorio para almacenar el proyecto y documentar su desarrollo. El archivo README será utilizado para presentar la descripción, objetivos, funcionalidades y características principales de la aplicación.

# Interfaz de usuario e interfaz de administrador

## Interfaz de usuario

La interfaz de usuario estará diseñada para ser sencilla y fácil de utilizar. La pantalla principal podrá mostrar los medicamentos registrados y las próximas dosis que deben tomarse. Entre las principales opciones de la interfaz estarán:

•	Registrar un nuevo medicamento.

•	Consultar los medicamentos registrados.

•	Modificar la información de un medicamento.

•	Eliminar un medicamento.

•	Consultar la dosis y horario correspondiente.

•	Marcar una dosis como tomada.

•	Marcar una dosis como omitida.

•	Consultar un historial básico de las tomas realizadas.

El diseño buscará que la información más importante, como el nombre del medicamento, la dosis y el horario, pueda identificarse rápidamente.

## Interfaz de administrador

La interfaz de administrador tendrá funciones diferentes a las del usuario común. Su propósito será permitir la administración básica de los usuarios y de algunos elementos generales de la aplicación. Entre sus posibles funciones estarán:

•	Consultar usuarios registrados.

•	Administrar las cuentas de los usuarios.

•	Gestionar categorías o unidades utilizadas por los medicamentos.  

•	Consultar información general relacionada con el funcionamiento de la aplicación.

Para la primera versión del proyecto, la interfaz de administrador se mantendrá sencilla, ya que la prioridad será desarrollar correctamente las funciones principales destinadas al usuario.

# Funcionalidad

La aplicación contará inicialmente con un sistema básico para registrar y controlar medicamentos. Al agregar un medicamento, el usuario deberá proporcionar la información necesaria, como nombre, dosis, unidad, horario y frecuencia. Una vez registrado, el medicamento aparecerá en la pantalla principal junto con la información correspondiente. Cuando llegue el momento indicado, la aplicación podrá mostrar un recordatorio para que el usuario registre si realizó la toma.

Las funciones principales propuestas son:

1. Registro de medicamentos: permite agregar nuevos medicamentos con su información correspondiente.
   
2. Consulta de medicamentos: permite visualizar los medicamentos registrados y sus horarios.
   
3. Edición de medicamentos: permite modificar información previamente registrada.
   
4. Eliminación: permite eliminar medicamentos que ya no sean necesarios dentro de la aplicación.
   
5. Control de tomas: permite registrar si una dosis fue tomada, quedó pendiente o fue omitida.
    
6. Recordatorios: permite avisar al usuario cuando se aproxime el horario establecido para una dosis.
    
7. Historial: permite consultar las tomas registradas anteriormente.
    
8. Control de cantidad: como una función adicional, podría permitir registrar la cantidad disponible de un medicamento y advertir cuando queden pocas dosis.
    
El desarrollo comenzará con las funciones esenciales de registro y consulta, dejando las funciones más avanzadas, como notificaciones e historial detallado, para etapas posteriores. De esta manera, el proyecto podrá desarrollarse de forma progresiva y adaptarse al nivel de experiencia adquirido durante el curso.

# Diseño

Diseño de interfaz: el diseño presentado representa una propuesta inicial de la estructura de la aplicación. Su propósito es definir la distribución de los elementos y el flujo de navegación antes de implementar el diseño visual definitivo.

De derecha a izquierda:

• Pantalla de inicio

• Agregar medicamento

• Lista de medicamento

• Historial de tomas

• Pantalla de administrador

<img width="2195" height="812" alt="Diseño App" src="https://github.com/user-attachments/assets/b5fdd667-b35e-489d-944d-c7675ad90869" />

## Registro de cambios (Changelog)

Esta sección presenta las actualizaciones realizadas, las mejoras que se encuentran actualmente en desarrollo y las funcionalidades que se tienen previstas para las siguientes etapas del proyecto MediTrack.

### Cambios pasados

* Se definió el concepto general de MediTrack como una aplicación móvil para el control y recordatorio de medicamentos.
* Se establecieron los datos principales que podrá registrar el usuario: nombre del medicamento, dosis, unidad, hora, frecuencia, fecha de inicio y fecha de finalización.
* Se diseñó la estructura inicial de las pantallas de la aplicación mediante wireframes.
* Se configuró el proyecto en Android Studio utilizando Kotlin y Jetpack Compose.
* Se estableció el nombre del proyecto como MediTrack y el paquete de la aplicación como `com.jorge.meditrack`.
* Se configuró el proyecto para utilizar como versión mínima Android 7.0 (API 24).
* Se creó la primera versión de la pantalla principal, incluyendo el nombre de la aplicación, la sección de próxima toma y el botón para agregar medicamentos.
* Se desarrolló la pantalla para agregar medicamentos.
* Se incorporaron selectores para la hora y las fechas de inicio y finalización.
* Se incorporaron opciones para seleccionar la unidad del medicamento y la frecuencia de administración.
* Se implementó la validación básica de los campos principales antes de guardar un medicamento.
* Se implementó el registro de varios medicamentos durante la ejecución de la aplicación.
* Se creó una sección de "Mis medicamentos" para visualizar los medicamentos registrados.
* Se agregó la opción de eliminar medicamentos de la lista.
* Se creó un repositorio en GitHub para mantener un historial de las versiones y avances del proyecto.

### Cambios actuales

* Se está mejorando la estructura de navegación entre la pantalla principal, el formulario de registro y la lista de medicamentos.
* Se está revisando la organización de las opciones de frecuencia para evitar alternativas que puedan resultar confusas para el usuario, como "Cada 24 horas" y "Una vez al día".
* Se está trabajando en mantener una interfaz sencilla y fácil de utilizar, especialmente durante el registro y consulta de medicamentos.
* Actualmente, los medicamentos se mantienen en memoria durante la ejecución de la aplicación. El almacenamiento permanente todavía se encuentra pendiente de implementación.

### Cambios futuros

* Implementar una base de datos local para almacenar permanentemente los medicamentos registrados.
* Incorporar la edición de medicamentos existentes.
* Desarrollar un historial de tomas que permita registrar medicamentos tomados, pendientes u omitidos.
* Implementar notificaciones para recordar al usuario las horas programadas para cada medicamento.
* Mejorar la lógica de las frecuencias para generar correctamente las próximas tomas según la configuración de cada medicamento.
* Desarrollar la pantalla de administración para gestionar la información correspondiente al sistema.
* Realizar pruebas de funcionamiento y usabilidad en diferentes dispositivos Android.
* Mejorar progresivamente el diseño visual y la experiencia de usuario.
* Completar la documentación técnica y preparar la versión final del proyecto para su presentación.
