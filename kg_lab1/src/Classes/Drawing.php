<?php

namespace lab\Classes;

use lab\Interfaces\ICanvas;
use lab\Interfaces\ICircleAlgo;
use lab\Interfaces\ILineAlgo;

class Drawing
{
    private ICanvas $canvas;
    private ?ILineAlgo $lineAlgo;
    private ?ICircleAlgo $circleAlgo;
    
    public float $lineTime   = 0;
    public float $circleTime = 0;
    
    function __construct(ICanvas $canvas, ?ILineAlgo $lineAlgo, ?ICircleAlgo $circleAlgo)
    {
        $this->canvas = $canvas;
        $this->lineAlgo = $lineAlgo;
        $this->circleAlgo = $circleAlgo;
    }
    
    private function isPointValid(Point $point): bool
    {
        return ($point->x <= $this->canvas->getWidth())
            && ($point->y <= $this->canvas->getHeight());
    }
    
    function drawLine(Point $begin, Point $end): bool
    {
        if(is_null($this->lineAlgo) || !$this->isPointValid($begin) || !$this->isPointValid($end))
            return false;
        
        $start = microtime(true);
        $this->lineAlgo->drawLine($this->canvas, $begin, $end);
        $this->lineTime += microtime(true) - $start;
        
        return true;
    }
    
    function drawCircle(Point $center, int $radius): bool
    {
        if(is_null($this->circleAlgo) || !$this->isPointValid($center))
            return false;
        
        $start = microtime(true);
        $this->circleAlgo->drawCircle($this->canvas, $center, $radius);
        $this->circleTime += microtime(true) - $start;
        
        return true;
    }
    
    function getResult(): string
    {
        return $this->canvas->render();
    }
}