# SalesCloud

Arquitectura de microservicios para gestión de usuarios, productos y ventas.

## Arquitectura

```text
Cliente -> api-gateway:8080 -> auth-service:8081 -> db_auth
                         -> product-service:8082 -> db_product
                         -> sales-service:8083 -> db_sales
service-registry (Eureka):8761
```

Los servicios se registran en Eureka y el gateway usa balanceo mediante `lb://`. `sales-service` consulta el precio de los productos con Resilience4j y un fallback cuando el catálogo no está disponible.

## Requisitos

- Java 21
- Maven 3.9+ o los `mvnw` incluidos
- Docker Desktop y Docker Compose

## Ejecución local recomendada

1. Construir los JAR:

```bash
for service in service-registry auth-service product-service sales-service api-gateway; do
  (cd "$service" && ./mvnw clean test package)
done
```

2. Levantar PostgreSQL y los servicios:

```bash
docker compose up --build
```

Compose espera a que PostgreSQL, Eureka y los microservicios estén saludables antes de iniciar el gateway. Si se modifica el código, se puede repetir el mismo comando para reconstruir las imágenes.

3. Verificar:

- Eureka: http://localhost:8761
- Gateway: http://localhost:8080
- Health: `http://localhost:8080/actuator/health`

Las credenciales locales de PostgreSQL son `postgres/postgres`. Las bases `db_auth`, `db_product` y `db_sales` se crean automáticamente desde `infra/postgres/init.sql`.

## Endpoints principales

Todos pueden ejecutarse a través del gateway:

```text
GET/POST       /products
GET/PUT/DELETE /products/{id}

POST           /auth/register
POST           /auth/login
GET            /auth/users
GET/PUT/DELETE /auth/users/{id}

GET/POST       /sales
GET/PUT/DELETE /sales/{id}
```

Ejemplo de producto:

```json
{
  "name": "Notebook",
  "description": "Equipo portátil",
  "price": 799990,
  "stock": 10
}
```

Ejemplo de venta:

```json
{
  "customerId": 1,
  "details": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

## Git y evaluación

La rama estable es `main`; se recomienda trabajar desde `develop` y usar ramas `feature/*`. Los comandos de Maven exigidos son `clean`, `test` y `package`. La colección de pruebas para Postman está en `docs/SalesCloud.postman_collection.json`.
