const Mongoose  = require('mongoose')


const dataSchema = new Mongoose.Schema(

    {
        nombre:{
            type: String,
            required: true,
            trim: true
        },
        descripcion:{
            type: String,
            required: true,
            trim: true
        },
        ubicacion:{
            type: String,
            required: true,
            trim: true
        },
        precio:{
            type:  Number,
            required: true,
            
        },
        calificacion:{
            type: Number,
            required: true,
        },
        imagenes: {
            type: [String],
            trim: true,
            required: true,
            default: []
        },
        fecha: {
            type: Date,
            required: true,
            trim: true
        },
        user: {
            type: Mongoose.Schema.Types.ObjectId,
            ref: "user",
         // No requerido solo para pruebas
         //   required: true
        },
           

    }
);

module.exports = Mongoose.model('product', dataSchema);