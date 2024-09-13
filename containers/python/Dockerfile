# Usa un'immagine base ufficiale di Python
FROM python:3.10-slim

# Aggiorna pip
RUN pip install --upgrade pip

# Imposta la variabile d'ambiente per disabilitare il buffering
ENV PYTHONUNBUFFERED=1

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il file requirements.txt e installa le dipendenze Python
COPY requirements.txt .
RUN pip3 install --no-cache-dir -r requirements.txt

# Copia il contenuto della tua applicazione nel container
COPY . .

# Espone la porta che verrà usata dall'applicazione Django
EXPOSE 8000

# Esegui le migrazioni al database e avvia il server di sviluppo Django
CMD ["sh", "-c", "python3 manage.py makemigrations && python3 manage.py migrate && python3 manage.py runserver 0.0.0.0:8000"]

