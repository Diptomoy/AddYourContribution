const File = require('./models/file');
const fs = require('fs');
const connectDB = require('./config/db');
connectDB();

async function fetchData() {
    // 24 hours old file fetch from dataBase

    const pastDate = new Date(Date.now() - 7*24*60*60*1000);
    
    const files = await File.find({ createdAt: {$lt: pastDate} });
    if(files.length) {
        for (const file of files) {
           try{
            fs.unlinkSync(file.path);
            await file.remove();

            console.log(`successfully deleted ${file.filename}`);
           } catch(err) {
               console.log(`Error while deleting file ${err}`);
           }
        }
        console.log(`Job Done!`);
    }
}

fetchData().then(process.exit);