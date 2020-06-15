import App from './app';
import { PdfController } from './controllers'

const app = new App([
    new PdfController() 
]);

app.listen();
