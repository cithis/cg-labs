<?php

namespace lab\Classes;

use lab\Interfaces\ICanvas;

class ImagickCanvasImpl implements ICanvas
{
    private int $height;
    private int $width;
    private int $pixelSize;
    private \Imagick $image;
    
    public function __construct(int $height, int $width, int $pixelSize)
    {
        $this->height    = $height;
        $this->width     = $width;
        $this->pixelSize = $pixelSize;
        
        $this->image = new \Imagick;
        $this->image->newImage($width * $pixelSize, $height * $pixelSize, "transparent");
        $this->image->setAntiAlias(false);
        $this->image->setImageFormat("png");
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
        $draw  = new \ImagickDraw();
        $proto = new \ImagickPixel($color);
        $draw->setStrokeAntialias(false);
        $draw->setStrokeColor($proto);
        $draw->setFillColor($proto);
        $draw->setStrokeOpacity(0);
        $draw->setStrokeWidth(0);
        
        $Ax = $at->x * $this->pixelSize;
        $Ay = $at->y * $this->pixelSize;
        $Bx = $Ax + $this->pixelSize - 1;
        $By = $Ay + $this->pixelSize - 1;
        
        $draw->rectangle($Ax, $Ay, $Bx, $By);
        $this->image->drawImage($draw);
        unset($draw);
    }
    
    function render(): string
    {
        return $this->image->getImageBlob();
    }
}