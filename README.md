# Nequi Franchise API

API REST reactiva para la gestión de franquicias, sucursales y productos desarrollada con Spring Boot 3, WebFlux y arquitectura hexagonal.

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring WebFlux** (Reactivo)
- **R2DBC** (Conexión reactiva a base de datos)
- **MySQL 8.0**
- **Gradle 8.14.3**
- **Docker & Docker Compose**
- **Arquitectura Hexagonal (Clean Architecture)**

## 📋 Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

- **Java 21** o superior
- **Gradle 8.14.3** o superior
- **Docker** y **Docker Compose**
- **Git**

### Verificar instalaciones

```bash
# Verificar Java
java -version

# Verificar Gradle
./gradlew --version

# Verificar Docker
docker --version
docker-compose --version
```

## 🏗️ Arquitectura del Proyecto

El proyecto sigue la arquitectura hexagonal (Clean Architecture) con la siguiente estructura:

```
nequi-franchise-api/
├── applications/
│   └── app-service/          # Aplicación principal
├── domain/
│   ├── model/               # Entidades y reglas de negocio
│   └── usecase/             # Casos de uso
├── infrastructure/
│   ├── driven-adapters/     # Adaptadores de salida (BD, APIs externas)
│   └── entry-points/        # Adaptadores de entrada (REST API)
└── deployment/              # Configuración de despliegue
```

## 🚀 Despliegue Local

### Opción 1: Despliegue con Docker Compose (Recomendado)

1. **Clonar el repositorio**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd nequi-franchise-api
   ```

2. **Levantar el api y la base de datos MySQL, tener en cuenta los pasos de construir la imagen con docker,
   por defecto el docker-compose tomara la imagen de dockerhub miltonsanchez/nequi-franchise-api:latest**
   ```bash
   docker-compose up -d
   ```

3. **Verificar que MySQL esté funcionando**
   ```bash
   docker-compose ps
   ```

4. **Ejecutar la aplicación**
   ```bash
   ./gradlew bootRun
   ```

### Opción 2: Despliegue Manual

1. **Configurar MySQL localmente**
   - Instalar MySQL 8.0
   - Crear base de datos: `nequi_franchise`
   - Ejecutar el script `db.sql` para crear las tablas

2. **Configurar variables de entorno**
   ```bash
   export SPRING_R2DBC_URL=r2dbc:mysql://localhost:3306/nequi_franchise
   export SPRING_R2DBC_USERNAME=root
   export SPRING_R2DBC_PASSWORD=password
   ```

3. **Ejecutar la aplicación**
   ```bash
   ./gradlew bootRun
   ```

## 🧪 Ejecutar Tests

### Ejecutar todos los tests
```bash
./gradlew test
```

### Ejecutar tests específicos
```bash
# Tests de la API
./gradlew test --tests "RouterRestTest"

# Tests de casos de uso
./gradlew test --tests "FranchiseUseCaseTest"

# Tests de infraestructura
./gradlew test --tests "*Repository*"
```

### Generar reportes de cobertura
```bash
./gradlew jacocoMergedReport
```

## 📊 Endpoints de la API

### Franquicias

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/franchise` | Crear una nueva franquicia |
| PUT | `/api/franchise/{id}/name` | Actualizar nombre de franquicia |

### Sucursales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/sucursal` | Crear una nueva sucursal |
| PUT | `/api/sucursal/{id}/name` | Actualizar nombre de sucursal |

### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/product` | Crear un nuevo producto |
| PUT | `/api/product/{id}/stock` | Actualizar stock de producto |
| PUT | `/api/product/{id}/name` | Actualizar nombre de producto |
| DELETE | `/api/sucursal/{sucursalId}/product/{productId}` | Eliminar producto |
| GET | `/api/franchise/{franchiseId}/products/max-stock` | Obtener productos con máximo stock por franquicia |

## 📝 Ejemplos de Uso

### Crear una franquicia
```bash
curl -X POST http://localhost:8080/api/franchise \
  -H "Content-Type: application/json" \
  -d '{"name": "Mi Franquicia"}'
```

### Crear una sucursal
```bash
curl -X POST http://localhost:8080/api/sucursal \
  -H "Content-Type: application/json" \
  -d '{"franchiseId": 1, "name": "Sucursal Centro"}'
```

### Crear un producto
```bash
curl -X POST http://localhost:8080/api/product \
  -H "Content-Type: application/json" \
  -d '{"sucursalId": 1, "name": "Producto A", "stock": 100}'
```

### Actualizar stock de producto
```bash
curl -X PUT http://localhost:8080/api/product/1/stock \
  -H "Content-Type: application/json" \
  -d '{"stock": 150}'
```

## 🔧 Configuración

### Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto del servidor | 8080 |
| `SPRING_R2DBC_URL` | URL de conexión a MySQL | r2dbc:mysql://localhost:3306/nequi_franchise |
| `SPRING_R2DBC_USERNAME` | Usuario de MySQL | root |
| `SPRING_R2DBC_PASSWORD` | Contraseña de MySQL | password |

### Configuración de CORS

La API está configurada para aceptar peticiones desde:
- `http://localhost:4200` (Angular)
- `http://localhost:8080` (Aplicación)

## 🐳 Docker

### Construir imagen Docker
```bash
  docker build -t miltonsanchez/nequi-franchise-api . -f deployment/Dockerfile
```
### Publicar imagen Docker en Docker Hub
```bash
  docker login
```
```bash
  docker tag miltonsanchez/nequi-franchise-api:latest miltonsanchez/nequi-franchise-api:latest
```
```bash
  docker push miltonsanchez/nequi-franchise-api:latest
```
### Ejecutar con Docker
```bash
  docker run -p 8080:8080 miltonsanchez/nequi-franchise-api:latest
```

## ☁️ Despliegue en AWS

### Base de Datos RDS con CloudFormation

El proyecto incluye un template de CloudFormation para crear una instancia RDS MySQL en AWS. El archivo `rds-nequi-franchise.yml` define una instancia MySQL 8.0 con las siguientes características:

#### Parámetros principales:
- **DBInstanceIdentifier**: `nequi-franchise`
- **EngineVersion**: MySQL 8.0.42
- **DBInstanceClass**: db.t4g.micro
- **AllocatedStorage**: 400 GB (gp2)
- **MaxAllocatedStorage**: 1000 GB (autoscaling)
- **StorageEncrypted**: true (cifrado habilitado)
- **PubliclyAccessible**: true
- **MultiAZ**: false

#### Desplegar la base de datos:

1. **Usando AWS CLI**:
   ```bash
   aws cloudformation create-stack \
     --stack-name nequi-franchise-rds \
     --template-body file://rds-nequi-franchise.yml \
     --parameters ParameterKey=MasterUserPassword,ParameterValue=TuContraseñaSegura \
     --capabilities CAPABILITY_IAM
   ```

2. **Usando AWS Console**:
   - Ir a CloudFormation en AWS Console
   - Crear stack nuevo
   - Subir el archivo `rds-nequi-franchise.yml`
   - Configurar parámetros (especialmente la contraseña)
   - Crear stack

3. **Verificar el despliegue**:
   ```bash
   aws cloudformation describe-stacks --stack-name nequi-franchise-rds
   ```

#### Configurar la aplicación para RDS:

Una vez desplegada la base de datos, actualiza las variables de entorno:

```bash
export SPRING_R2DBC_URL=r2dbc:mysql://[ENDPOINT_RDS]:3306/nequi_franchise
export SPRING_R2DBC_USERNAME=root
export SPRING_R2DBC_PASSWORD=TuContraseñaSegura
```

#### Obtener el endpoint de RDS:

```bash
aws cloudformation describe-stacks \
  --stack-name nequi-franchise-rds \
  --query 'Stacks[0].Outputs[?OutputKey==`DBEndpointAddress`].OutputValue' \
  --output text
```

### Consideraciones de seguridad:

- La instancia RDS está configurada con cifrado de almacenamiento
- Se recomienda configurar Security Groups apropiados
- Considerar habilitar Multi-AZ para producción
- Configurar backups automáticos según necesidades

### Estado del Despliegue en AWS

La aplicación se encuentra desplegada exitosamente en AWS con los siguientes recursos:

#### CloudFormation Stack
![CloudFormation Stack](images/CloudFormation%20Stack.png)
*Stack de CloudFormation "aws-cf-stack-nequi" desplegado exitosamente*

#### Base de Datos RDS
![RDS Database](images/Base%20de%20Datos%20RDS.png)
*Instancia RDS MySQL "nequi-franchise" disponible y funcionando*

#### Instancia EC2
![EC2 Instance](images/Instancia%20EC2.png)
*Instancia EC2 "ec2-nequi" ejecutándose en us-east-1a*

### Ejemplo de Uso en AWS

Una vez desplegada la aplicación, puedes probar los endpoints usando la instancia EC2:

```bash
# Obtener productos con máximo stock por franquicia
curl --location 'http://ec2-98-87-18-51.compute-1.amazonaws.com:8080/api/franchise/1/products/max-stock'

# Crear una nueva franquicia
curl --location 'http://ec2-98-87-18-51.compute-1.amazonaws.com:8080/api/franchise' \
  --header 'Content-Type: application/json' \
  --data '{"name": "Mi Franquicia AWS"}'

# Crear una sucursal
curl --location 'http://ec2-98-87-18-51.compute-1.amazonaws.com:8080/api/sucursal' \
  --header 'Content-Type: application/json' \
  --data '{"franchiseId": 1, "name": "Sucursal AWS"}'

# Crear un producto
curl --location 'http://ec2-98-87-18-51.compute-1.amazonaws.com:8080/api/product' \
  --header 'Content-Type: application/json' \
  --data '{"sucursalId": 1, "name": "Producto AWS", "stock": 100}'
```

**Nota**: Reemplaza la IP `98.87.18.51` con la IP pública actual de tu instancia EC2 si ha cambiado.

## 📈 Monitoreo

### Health Check
```bash
 curl http://localhost:8080/actuator/health
```

### Métricas Prometheus
```bash
 curl http://localhost:8080/actuator/prometheus
```

## 🛠️ Desarrollo

### Estructura de la base de datos

```sql
-- Franquicias
CREATE TABLE franchise (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Sucursales
CREATE TABLE sucursal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    franchise_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (franchise_id) REFERENCES franchise(id) ON DELETE CASCADE
);

-- Productos
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sucursal_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (sucursal_id) REFERENCES sucursal(id) ON DELETE CASCADE
);
```

### Comandos útiles

```bash
# Limpiar y construir
./gradlew clean build

# Ejecutar tests con reportes
./gradlew test jacocoMergedReport

# Ejecutar análisis estático
./gradlew sonarqube

# Generar documentación
./gradlew javadoc

# Verificar estructura del proyecto
./gradlew validateStructure
```

## 🚨 Solución de Problemas

### Error de conexión a MySQL
- Verificar que MySQL esté ejecutándose: `docker-compose ps`
- Verificar credenciales en `application.yaml`
- Revisar logs: `docker-compose logs mysql`

### Puerto ocupado
- Cambiar puerto en `application.yaml`: `server.port: 8081`
- Verificar procesos: `netstat -tulpn | grep 8080`

### Error de compilación
- Verificar versión de Java: `java -version`
- Limpiar cache: `./gradlew clean`
- Actualizar dependencias: `./gradlew --refresh-dependencies`

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

## 👥 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📞 Contacto

Para preguntas o soporte, contacta al equipo de desarrollo:

- **Email**: tomilton@hotmail.com
- **Email**: milton.sanchez7@gmail.com
- **Teléfono**: 3127340763
