const { body, param } = require('express-validator');
const validators = {};

validators.createPostValidator = [
param("id")
    .optional()
    .isMongoId().withMessage("Identifier must be a Mongo Id"),


];

validators.idInParams = [
    param("id")
    .notEmpty().withMessage("El id es requerido")
    .isMongoId().withMessage("El id no es válido")
];

validators.idenInParams = [
    param("id")
    .notEmpty().withMessage("El id es requerido")
    .isMongoId().withMessage("El id no es válido")
];

validators.saveCommentValidator = [
    body("content")
        .notEmpty().withMessage("El contenido es requerido")
        .isLength({ max: 280 }).withMessage("La longitud maxima del comentario es de 280 caracteres"),
    body("_id")
        .optional()
        .notEmpty().withMessage("_id es requerido")
        .isMongoId().withMessage("El id no es válido")
];

module.exports = validators;