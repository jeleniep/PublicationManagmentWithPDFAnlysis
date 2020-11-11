import App from './app';
import { PublicationsController, UsersController, CommentsController } from './controllers'

const app = new App([
    new PublicationsController(),
    new UsersController(),
    new CommentsController()
]);

app.listen();
