# Usa un'immagine base ufficiale di Node.js
FROM node:18

# Imposta la directory di lavoro
WORKDIR /app

# Copia i file di package e package-lock (o yarn.lock)
COPY package*.json ./

# Installa le dipendenze dell'app
RUN npm install

# Copia tutto il codice sorgente dell'app
COPY . .

# Espone la porta che Expo utilizza per lo sviluppo
EXPOSE 19000
EXPOSE 19001
EXPOSE 19002

# Avvia il server Expo
CMD ["npx", "expo", "start", "--tunnel"]
