<?php

namespace lab\Classes;

use lab\Interfaces\ICanvas;

class GdCanvasImpl implements ICanvas {
    private int $height;
    private int $width;
    private int $pixelSize;
    private \GdImage $image;
    private int $bleach;
    
    public function __construct(int $height, int $width, int $pixelSize)
    {
        $this->height    = $height;
        $this->width     = $width;
        $this->pixelSize = $pixelSize;
        
        $this->image = imagecreatetruecolor($width * $pixelSize, $height * $pixelSize);
        imagefill($this->image, 0, 0, imagecolorallocate($this->image, 255, 255, 255));
        imagesavealpha($this->image, true);
        
        $this->bleach = imagecolorallocate($this->image, 255, 255, 255);
    }
    
    private function color(string $color): int|bool
    {
        if($color === "black")
            return imagecolorallocatealpha($this->image, 0, 0, 0, 1);
        
        if(preg_match("%rgba\((\d+),\s+(\d+),\s+(\d+),\s+(\d+(?:\.\d+)?)\)%", $color, $c)) {
            return imagecolorallocatealpha($this->image, $c[1], $c[2], $c[3], round($c[4] * 100, 0, PHP_ROUND_HALF_ODD));
        }
        
        return false;
    }
    
    function getHeight(): int
    {
        return $this->height;
    }
    
    function getWidth(): int
    {
        return $this->width;
    }
    
    function setPixel(Point $at, string $color): void
    {
        $Ax = $at->x * $this->pixelSize;
        $Ay = $at->y * $this->pixelSize;
    
        $color = $this->color($color);
        for($i = 0; $i < $this->pixelSize; $i++) {
            for ($j = 0; $j < $this->pixelSize; $j++) {
                imagesetpixel($this->image, $Ax + $i, $Ay + $j, $this->bleach);
                imagesetpixel($this->image, $Ax + $i, $Ay + $j, $color);
            }
        }
    }
    
    function render(): string
    {
        ob_start();
        imagepng($this->image);
        $blob = ob_get_contents();
        ob_end_clean();
        
        return $blob;
    }
}