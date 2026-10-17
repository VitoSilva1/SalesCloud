# Guion de presentación (3-8 minutos)

1. Mostrar la arquitectura: gateway, Eureka, auth, product, sales y las tres bases PostgreSQL.
2. Ejecutar `docker compose up --build` y mostrar Eureka con los servicios registrados.
3. En Postman, crear y listar un producto desde `http://localhost:8080/products`.
4. Actualizar y eliminar el producto; mostrar también `GET /products/999999` con respuesta 404.
5. Registrar un usuario y probar login correcto e incorrecto.
6. Crear una venta con el `productId` creado y mostrar el total persistido.
7. Explicar brevemente `model`, `repository`, `service` y `controller`.
8. Mostrar `mvn clean test package` y los cinco JAR generados.
9. Mostrar el README y explicar cómo clonar y ejecutar el proyecto.

La narración debe ser realizada por los estudiantes, con audio claro y sin ruido de fondo.
