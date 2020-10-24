import App from './app';
import { PublicationsController } from './controllers'

const app = new App([
    new PublicationsController() 
]);

app.listen();
