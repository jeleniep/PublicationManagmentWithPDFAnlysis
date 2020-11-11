import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class EditCommentDto {
    
    @IsOptional()
    @IsString()
    public readonly body: string;

    @IsOptional()
    @IsString()
    public readonly publicationId: string;

}

export default EditCommentDto;
