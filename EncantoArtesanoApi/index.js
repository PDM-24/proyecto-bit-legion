require ('dotenv').config();

const express = require('express');
const mongoose = require('mongoose');
const mongoString = process.env.DATABASE_URL;
const routes = require('./route/index.routes');
const logger = require('morgan');
const cors = require('cors');
let debug = require('debug')('app:server');

const app = express()
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cors());

mongoose.connect(mongoString);
const database = mongoose.connection;

database.on('error', (error) =>{
    console.log(error);
})

database.once('connected', () =>{
    console.log('database connected');
})



app.use('/api', routes)

app.listen(3000, () =>{
    console.log('server started at port 3000')
})