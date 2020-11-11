import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class AddCommentDto {
    
    @IsString()
    public readonly body: string;

    @IsString()
    public readonly publicationId: string;

}

export default AddCommentDto;
