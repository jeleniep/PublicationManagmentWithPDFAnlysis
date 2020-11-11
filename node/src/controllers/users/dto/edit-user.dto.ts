import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail, IsBoolean } from 'class-validator';
import { Type } from 'class-transformer';

class EditUserDto {
    
    @IsOptional()
    @IsEmail()
    public readonly email: string;

    @IsOptional()
    @IsString()
    public readonly username: string;

    @IsOptional()
    @IsString()
    public readonly profile: string;


}

export default EditUserDto;
