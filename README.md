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

2. **Levantar la base de datos MySQL**
   ```bash
   docker-compose up -d mysql
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
./gradlew bootBuildImage
```

### Ejecutar con Docker
```bash
docker run -p 8080:8080 nequi-franchise-api:latest
```

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
