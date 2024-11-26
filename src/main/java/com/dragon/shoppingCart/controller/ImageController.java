package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.entity.Image;
import com.dragon.shoppingCart.exception.ImageNotFoundException;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestController
@RequestMapping("/api/image")
public class ImageController {

    ImageService imageService;
    ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try{
            List<ImageDto> imageDtoList = imageService.addImages(files,productId);
            return ResponseEntity.ok(new ApiResponse("the images list are added successfully",imageDtoList));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("the images list are added successfully",e.getMessage()));
        }

    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image img = imageService.getImageById(imageId);
        //we need to create a resource in order to return file
        ByteArrayResource byteArrayResource =
                new ByteArrayResource(img.getProductImage()
                        .getBytes(1,(int) img.getProductImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(img.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +img.getFileName() + "\"")
                .body(byteArrayResource);
    }


    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file) {
        try {
            Image updatedImage = imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Image updated successfully", updatedImage));
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Image not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to update image", null));
        }
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Long imageId){
        imageService.deleteImage(imageId);
    }
}
