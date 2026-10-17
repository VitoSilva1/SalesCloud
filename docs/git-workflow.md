# Flujo Git recomendado para la entrega

```bash
git checkout -b develop
git checkout -b feature/product-service
git add product-service
git commit -m "feat(product): add product CRUD"
git checkout develop
git checkout -b feature/auth-service
git add auth-service
git commit -m "feat(auth): add registration and login"
```

Repetir el patrón para `feature/sales-service`, `feature/api-gateway` y `feature/infrastructure`. Finalmente integrar las features en `develop` y luego en `main`, conservando commits descriptivos.
