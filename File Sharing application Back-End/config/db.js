require('dotenv').config();
const mongoose = require('mongoose');
function connectDB() {
    // Database connection 🥳
    mongoose.connect(process.env.MONGO_CONNECTION_URL, { useNewUrlParser: true, useUnifiedTopology: true });
    const connection = mongoose.connection;
    connection.once('open', function() {
        console.log('Database connected 🥳🥳🥳🥳');
    }).on( 'error', function(error) {
        console.log('Connection failed ☹️☹️☹️☹️');
    });
}

// mIAY0a6u1ByJsWWZ, useFindAndModify : true useCreateIndex:true,
// , err => {
//     if(err) throw err;
//     console.log('Connected to MongoDB!!!')
//  }
module.exports = connectDB;