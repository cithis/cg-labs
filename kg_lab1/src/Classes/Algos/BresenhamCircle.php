<?php

namespace lab\Classes\Algos;

use lab\Classes\Point;
use lab\Interfaces\ICanvas;
use lab\Interfaces\ICircleAlgo;

class BresenhamCircle implements ICircleAlgo
{
    function drawCircle(ICanvas $canvas, Point $center, int $radius): void
    {
        $x     = 0;
        $y     = $radius;
        $delta = 1 - 2 * $radius;
        $error = 0;
        
        while($y >= 0) {
            $canvas->setPixel(new Point($center->x + $x, $center->y + $y), "black");
            $canvas->setPixel(new Point($center->x + $x, $center->y - $y), "black");
            $canvas->setPixel(new Point($center->x - $x, $center->y + $y), "black");
            $canvas->setPixel(new Point($center->x - $x, $center->y - $y), "black");
            
            $error = 2 * ($delta + $y) - 1;
            if($delta < 0 && $error <= 0) {
                $x++;
                $delta += 2 * $x + 1;
                continue;
            }
    
            $error = 2 * ($delta - $x) - 1;
            if($delta > 0 && $error > 0) {
                $y--;
                $delta += 1 - 2 * $y;
                continue;
            }
            
            $x++;
            $delta += 2 * ($x - $y);
            $y--;
        }
    }
}