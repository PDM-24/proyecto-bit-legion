require('dotenv').config();

const express = require('express');
const mongoose = require('mongoose');
const mongoString = process.env.DATABASE_URL;
const routes = require('./route/index.routes');
const uploadRoutes = require('./route/uploades.routes'); // Importar la ruta de subida
const logger = require('morgan');
const cors = require('cors');
let debug = require('debug')('app:server');

const app = express();
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cors());

// Conectar a la base de datos
mongoose.connect(mongoString);
const database = mongoose.connection;

database.on('error', (error) => {
    console.log(error);
});

database.once('connected', () => {
    console.log('database connected');
});

// Servir archivos estáticos desde la carpeta 'uploads'
app.use('/uploads', express.static('uploads'));

// Usar las rutas
app.use('/api', routes);
app.use('/api', uploadRoutes); // Usar la nueva ruta de subida

app.listen(3000, () => {
    console.log('server started at port 3000');
});