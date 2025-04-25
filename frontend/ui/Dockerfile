# Usar la imagen oficial de Caddy basada en Alpine (ligera)
FROM caddy:alpine

# Establecer el directorio de trabajo DENTRO del contenedor
# /srv es una convención común para datos servidos
WORKDIR /srv

# Copiar los artefactos de la compilación WasmJS desde el contexto de build
# (output/) al directorio de trabajo actual (/srv)
COPY output/ .

# Copiar el archivo de configuración de Caddy al lugar esperado dentro del contenedor
COPY Caddyfile /etc/caddy/Caddyfile

# Exponer el puerto en el que Caddy escuchará (según Caddyfile)
EXPOSE 8080

# El CMD por defecto de la imagen base ('caddy run --config /etc/caddy/Caddyfile')
# se encargará de iniciar Caddy con nuestra configuración.
