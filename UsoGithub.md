# Reporte Técnico de Práctica: Instalación y Uso de GitHub CLI

**Universidad Veracruzana** | **Facultad de Estadística e Informática**  
**Carrera:** Ingeniería en Tecnologías Computacionales  
**Curso:** Fundamentos de Redes de Computadoras  

**Estudiante:** David Silva Mercado  
**Fecha:** 22 de Mayo de 2026  

---

## 1. Introducción

El uso de sistemas de control de versiones es una competencia crítica en el desarrollo de software y la administración de entornos tecnológicos. Mientras que Git gestiona el historial y seguimiento de cambios de manera local en el espacio de trabajo, la plataforma GitHub provee servicios en la nube orientados a la colaboración, tales como la gestión de ramas remotas, rastreo de incidencias (*Issues*) y solicitudes de fusión (*Pull Requests*).

Tradicionalmente, interactuar con estas funciones web requería un cambio de contexto constante entre el editor de código, la terminal y el navegador de internet. **GitHub CLI (`gh`)** surge como la herramienta de interfaz de línea de comandos oficial que integra estas dos capas operativas en un único entorno, permitiendo automatizar flujos de trabajo completos de ingeniería directamente desde consolas como **Git Bash**.

---

## 2. Bitácora de Campo de la Práctica (Paso a Paso)

Esta sección describe el desarrollo cronológico de la práctica en el sistema operativo Windows, detallando los obstáculos técnicos reales encontrados en el entorno local y los comandos de bajo nivel aplicados para resolverlos.

### Paso 2.1: Instalación del Paquete
La instalación se realizó de manera automatizada invocando el gestor de paquetes nativo de Windows desde la terminal de Git Bash:

```bash
winget install --id GitHub.cli
```

### Paso 2.2: Configuración del PATH y Resolución del Error del Entorno
Al intentar comprobar el estado del binario ejecutable mediante el comando `gh --version`, el emulador arrojó un error de tipo `command not found`. Esto se debe a que Git Bash lee de forma estática las variables de entorno de Windows durante su inicialización y requiere una actualización manual en caliente.

* **Fallo detectado:** El manual guía indicaba exportar la ruta apuntando al volumen virtual secundario `/d/Program Files/...`. En el equipo local del estudiante, la instalación se ejecutó en la raíz global del disco principal `C:`.
* **Solución técnica:** Se redefinió la variable global apuntando a la partición correcta y se inyectó de forma permanente en el archivo de configuración del perfil del usuario para asegurar su persistencia en futuras sesiones de trabajo:

```bash
export PATH=$PATH:"/c/Program Files/GitHub CLI"
echo 'export PATH=$PATH:"/c/Program Files/GitHub CLI"' >> ~/.bashrc
source ~/.bashrc
```
### Paso 2.3: Enlace e Inicio de Sesión Seguro (Autenticación)
Para vincular la máquina de desarrollo con los servidores remotos se invocó el módulo de autenticación:

```bash
gh auth login
```
* **Resolución del Protocolo de Transporte:** Debido a que el script local iniciar_ssh.sh presentaba un error de sintaxis al intentar levantar el agente del sistema (ssh-agent-s: command not found), se seleccionó estratégicamente el protocolo alterno HTTPS para agilizar la práctica.

* **Validación en Navegador:** Se seleccionó la opción de inicio de sesión mediante navegador web, copiando el código alfanumérico único generado por la CLI y pegándolo en el portal de seguridad de GitHub. El sistema concluvió con el mensaje de confirmación: ✓ Logged in as dsilvamercado-web.

### Paso 2.4: Gestión del Ciclo de Vida del Repositorio y Ramas
Se inicializó un repositorio público en la plataforma web directamente desde la consola, procediendo a su clonación local y creación de una rama aislada de características:

```bash
# Creación remota del repositorio
gh repo create Repositorio3 --public

# Clonación del proyecto en el directorio de trabajo local
gh repo clone dsilvamercado-web/Repositorio3
cd Repositorio3

# Creación e intercambio automático a una rama de desarrollo
git checkout -b rama_1
```
* **Corrección de Sintaxis de Git:** Al registrar los cambios en el archivo codigo2.py, la consola arrojó una excepción debido a la omisión del espacio en blanco en la instrucción condensada (git commit-m). Se corrigió separando debidamente el modificador del mensaje:

```bash
git add .
git commit -m "cambios con cli"
git push origin rama_1
```
### Paso 2.5: Solicitud de Cambios (Pull Requests) y Control de Permisos Administrativos
Una vez publicadas las modificaciones en la nube, se abrió un Pull Request formal de integración desde la terminal:

```bash
gh pr create
```
Posteriormente, con el fin de evaluar las herramientas de destrucción segura de recursos, se elevaron los alcances (scopes) de las credenciales de la sesión local para habilitar permisos específicos de borrado corporativo (delete_repo):
```bash
# Refresco de permisos en el token local
gh auth refresh -h github.com -s delete_repo

# Eliminación segura del repositorio de pruebas
gh repo delete Repositorio3
```
---

## 3. Diccionario e Investigación de Comandos de GitHub CLI

Mediante la inspección profunda de la documentación contextual nativa del binario (`gh [comando] --help`), se investigaron y estructuraron los siguientes 12 comandos esenciales para la automatización avanzada de flujos de desarrollo colaborativo.

| Comando | Descripción y Utilidad Práctica | Sintaxis de Ejemplo |
| :--- | :--- | :--- |
| **1. `gh auth login`** | Inicializa el proceso interactivo de intercambio y validación de credenciales con la API de GitHub. | `gh auth login` |
| **2. `gh auth status`** | Diagnostica las sesiones activas en la terminal, indicando el usuario, servidor, protocolo (HTTPS/SSH) y permisos habilitados. | `gh auth status` |
| **3. `gh repo create`** | Instancia un repositorio nuevo directamente en el servidor. Admite banderas para configurar visibilidad, descripciones y plantillas base. | `gh repo create mi-proyecto --public` |
| **4. `gh repo clone`** | Descarga una copia exacta de un repositorio remoto mapeando el identificador corto (`usuario/repositorio`) de forma automática. | `gh repo clone usuario/proyecto` |
| **5. `gh repo list`** | Lista, ordena y filtra de forma tabular los repositorios de un usuario de acuerdo con variables como el lenguaje de programación principal. | `gh repo list --language java` |
| **6. `gh repo view`** | Despliega e imprime directamente en consola el archivo descriptivo `README.md` de un proyecto sin necesidad de descargarlo localmente. | `gh repo view usuario/proyecto` |
| **7. `gh pr create`** | Generar una propuesta formal de incorporación de cambios (*Pull Request*) asignando títulos, descripciones técnicas y revisores desde consola. | `gh pr create --title "Fix de Red"` |
| **8. `gh pr list`** | Muestra de manera organizada el estado de los Pull Requests activos, facilitando el control de flujos de integración del equipo. | `gh pr list --state open` |
| **9. `gh pr checkout`** | Sincroniza y cambia el entorno local a la rama de un Pull Request específico enviado por otro desarrollador para revisiones de código. | `gh pr checkout 3` |
| **10. `gh issue list`** | Consulta la base de datos de incidencias, errores documentados o historias de usuario asignadas dentro del proyecto. | `gh issue list --label "bug"` |
| **11. `gh issue create`** | Registra de forma inmediata una nueva tarea o reporte de fallo técnico en el repositorio central de GitHub durante fases de pruebas. | `gh issue create --title "Fallo de puerto"` |
| **12. `gh gist create`** | Comparte fragmentos aislados de código, configuraciones de red rápidos o notas de texto de manera pública o secreta sin crear un repositorio completo. | `gh gist create script.py --public` |

## 4 Documentación Detallada de Comandos

A continuación se detalla cada uno de los 12 comandos evaluados en el laboratorio, siguiendo la estructura requerida.

---

### 1. Nombre del comando: `gh auth login`

* **Descripción general de su función:** Permite iniciar sesión en la plataforma de GitHub desde la terminal local de manera interactiva.
* **Explicación breve de su propósito o utilidad:** Establece el vínculo de autenticación inicial seguro entre tu máquina de desarrollo y el servidor remoto de GitHub, permitiéndote operar comandos administrativos directamente con tu perfil.
* **Sintaxis básica de uso:**
```bash
  gh auth login [flags]
```
Ejemplo práctico de ejecución:

```bash
  gh auth login
  ```
2. Nombre del comando: gh auth status
Descripción general de su función: Muestra información detallada sobre el estado de la sesión de autenticación configurada en la terminal.

Explicación breve de su propósito o utilidad: Sirve para diagnosticar de manera rápida si hay una cuenta activa, indicando el nombre de usuario, el servidor de alojamiento (github.com o enterprise), el protocolo de transporte activo (HTTPS o SSH) y los tokens de seguridad validados.

Sintaxis básica de uso:

```bash
  gh auth status [flags]
```
Ejemplo práctico de ejecución:

```Bash
  gh auth status
  ```
3. Nombre del comando: gh repo create
Descripción general de su función: Instancia y crea un nuevo repositorio vacío directamente en los servidores de GitHub sin necesidad de acceder al navegador web.

Explicación breve de su propósito o utilidad: Agiliza el aprovisionamiento de nuevos proyectos de software, permitiendo definir desde la consola su nombre, nivel de visibilidad (público o privado), descripción inicial y la clonación automática local.

Sintaxis básica de uso:

```Bash
  gh repo create [name] [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh repo create Repositorio3 --public
  ```
4. Nombre del comando: gh repo clone
Descripción general de su función: Descarga una réplica exacta de un repositorio alojado en GitHub hacia el directorio de trabajo local del equipo.

Explicación breve de su propósito o utilidad: Configura de forma automática los punteros remotos (origin) mapeando el identificador del repositorio, permitiendo comenzar a trabajar inmediatamente sobre el código fuente del proyecto.

Sintaxis básica de uso:

```Bash
  gh repo clone <repository> [directory] [-- flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh repo clone dsilvamercado-web/Repositorio3
  ```
5. Nombre del comando: gh repo list
Descripción general de su función: Despliega una lista ordenada de forma tabular con los repositorios que pertenecen a un usuario u organización específica.

Explicación breve de su propósito o utilidad: Facilita la auditoría y navegación de los proyectos activos en la nube, admitiendo filtros avanzados por lenguaje de programación, visibilidad o límite de resultados directamente en la consola.

Sintaxis básica de uso:

```Bash
  gh repo list [owner] [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh repo list dsilvamercado-web --language java
  ```
6. Nombre del comando: gh repo view
Descripción general de su función: Muestra la información descriptiva y el contenido del archivo README.md de un repositorio en la terminal.

Explicación breve de su propósito o utilidad: Permite realizar una inspección rápida del propósito de un proyecto, sus requerimientos o su documentación técnica principal sin necesidad de clonar los archivos localmente o abrir la interfaz web.

Sintaxis básica de uso:

```Bash
  gh repo view [repository] [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh repo view dsilvamercado-web/Repositorio3
  ```
7. Nombre del comando: gh pr create
Descripción general de su función: Registra y genera una propuesta formal de incorporación de cambios (Pull Request) sobre un repositorio en GitHub.

Explicación breve de su propósito o utilidad: Automatiza el flujo de integración continua, permitiendo a los desarrolladores enviar sus aportes técnicos en ramas aisladas para que sean evaluadas por revisores, asignando títulos y descripciones operativas directamente desde los comandos.

Sintaxis básica de uso:

```Bash
  gh pr create [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh pr create --title "Fix de Red" --body "Corrección en archivo codigo2"
  ```
8. Nombre del comando: gh pr list
Descripción general de su función: Despliega de manera estructurada los Pull Requests activos o cerrados dentro del repositorio actual.

Explicación breve de su propósito o utilidad: Proporciona visibilidad del estado de los flujos de trabajo colaborativos del equipo, permitiendo verificar qué propuestas están abiertas, asignadas a revisión o pendientes de aprobación.

Sintaxis básica de uso:

```Bash
  gh pr list [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh pr list --state open
  ```
9. Nombre del comando: gh pr checkout
Descripción general de su función: Sincroniza y conmuta de forma local el entorno de trabajo hacia la rama específica vinculada a un Pull Request.

Explicación breve de su propósito o utilidad: Permite a un ingeniero descargar y probar localmente el código propuesto por otro miembro del equipo, facilitando tareas críticas de control de calidad, depuración de errores y revisión por pares (code review).

Sintaxis básica de uso:

```Bash
  gh pr checkout {<number> | <url> | <branch>} [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh pr checkout 3
  ```
10. Nombre del comando: gh issue list
Descripción general de su función: Consulta e imprime en la consola el listado de incidencias, errores documentados o tareas registradas en el proyecto.

Explicación breve de su propósito o utilidad: Centraliza el seguimiento de errores técnicos o requerimientos de desarrollo funcionales de la aplicación, agilizando el flujo de trabajo de mantenimiento sin salir del editor de código.

Sintaxis básica de uso:

```Bash
  gh issue list [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh issue list --label "bug"
  ```
11. Nombre del comando: gh issue create
Descripción general de su función: Da de alta y documenta una nueva incidencia (Issue) en el sistema de gestión del repositorio remoto.

Explicación breve de su propósito o utilidad: Permite registrar fallas en caliente, solicitudes de características nuevas (feature requests) o hitos de entrega durante las fases de pruebas operativas del software de manera ágil.

Sintaxis básica de uso:

```Bash
  gh issue create [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh issue create --title "Fallo de puerto" --body "Excepción de comunicación"
  ```
12. Nombre del comando: gh gist create
Descripción general de su función: Genera un recurso Gist en los servidores de GitHub para almacenar fragmentos aislados de código o notas de texto.

Explicación breve de su propósito o utilidad: Ideal para compartir de manera ágil pequeñas rutinas de scripts, archivos de configuración de redes de prueba o notas técnicas públicas o secretas sin tener que instanciar la estructura completa de un repositorio tradicional.

Sintaxis básica de uso:

```Bash
  gh gist create [<filename>...] [flags]
  ```
Ejemplo práctico de ejecución:

```Bash
  gh gist create script.py --public
  ```
## 5. Resultados Obtenidos
Como resultado directo del desarrollo de esta práctica de laboratorio, se alcanzaron los siguientes hitos técnicos en el entorno local y remoto:

* **Despliegue Exitoso del Entorno CLI:** Se logró la instalación y aprovisionamiento de GitHub CLI en el sistema operativo local, resolviendo los conflictos de indexación del PATH global de Windows a través del archivo de configuración ~.bashrc de Git Bash.

* **Autenticación e Interoperabilidad Segura:** Se validó el canal de comunicación cifrado con la API de GitHub mediante el protocolo HTTPS, enlazando la identidad digital local (dsilvamercado-web) de forma correcta tras mitigar la falla de dependencias del script de SSH.

* **Automatización del Ciclo de Vida del Software:** Se operaron de manera remota y sin intermediación de la interfaz web gráfica todas las fases de gestión de código distribuido, abarcando desde la instanciación de repositorios (Repositorio3), aislamiento de características en ramas (rama_1), envíos de confirmaciones (commits), hasta la apertura formal de solicitudes de fusión (Pull Requests).

* **Control de Alcance y Destrucción Segura:** Se validó la elevación dinámica de privilegios de seguridad (scopes) en caliente mediante el refresco de tokens (delete_repo), permitiendo la depuración y remoción controlada de recursos directamente desde la terminal.

## 6. Conclusión

La adopción de GitHub CLI representa una mejora sustancial en el rendimiento y perfil operativo de un Ingeniero en Tecnologías Computacionales. El desarrollo de esta actividad práctica permitió consolidar conocimientos esenciales de sistemas: la manipulación y persistencia de variables de entorno globales del sistema de archivos (`PATH`), el análisis sintáctico de argumentos en interfaces de línea de comandos, la mitigación de errores de red o scripts locales (agente SSH) mediante la conmutación transparente a HTTPS, y la administración lógica de código distribuido. Esta serie de destrezas forman los cimientos necesarios para el diseño de infraestructura de software moderna bajo estándares profesionales de automatización.