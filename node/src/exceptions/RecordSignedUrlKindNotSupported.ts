import HttpException from "./HttpException";

class RecordSignedUrlKindNotSupported extends HttpException {
    constructor() {
        super(
            404,
            'signed-url-action-not-supported',
            'Provided "action" is not supported for signed url. Try "view/download/upload"'
        );
    }
}

export default RecordSignedUrlKindNotSupported;
