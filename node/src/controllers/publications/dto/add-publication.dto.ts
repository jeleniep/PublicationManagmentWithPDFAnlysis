import { IsString, ArrayMinSize, IsObject, ValidateNested, IsArray, IsOptional, IsEmail } from 'class-validator';
import { Type } from 'class-transformer';

class AddPublicationDto {
    
    @IsString()
    public readonly name: string;

    @IsString()
    public readonly description: string;

    @IsString()
    public readonly file: string;

    @IsArray()
    @ValidateNested({ each: true })
    @ArrayMinSize(0)
    @Type(() => String)        
    public readonly authors: string[];

    @IsOptional()
    @IsArray()
    @ValidateNested({ each: true })
    @ArrayMinSize(0)
    @Type(() => String)        
    public readonly tags: string[];

    @IsOptional()
    @IsArray()
    @ValidateNested({ each: true })
    @ArrayMinSize(0)
    @Type(() => String)        
    public readonly owners: string;

}

export default AddPublicationDto;
