<?php

namespace lab\Classes\Algos;

use lab\Classes\Point;
use lab\Interfaces\ICanvas;
use lab\Interfaces\ILineAlgo;

class BresenhamLine implements ILineAlgo
{
    function drawLine(ICanvas $canvas, Point $begin, Point $end): void
    {
        $dx = abs($end->x - $begin->x);
        $dy = abs($end->y - $begin->y);
        
        $steep = abs($dy) > abs($dx);
        if($steep) {
            [$begin->x, $begin->y] = [$begin->y, $begin->x];
            [$end->x, $end->y]     = [$end->y, $end->x];
        }
        
        if($begin->x > $end->x) {
            [$begin->x, $end->x] = [$end->x, $begin->x];
            [$begin->y, $end->y] = [$end->y, $begin->y];
        }
    
        $dx = abs($end->x - $begin->x);
        $dy = abs($end->y - $begin->y);
        
        $error = abs($dx / 2);
        $yStep = ($end->y - $begin->y) <=> 0;
        
        $y = $begin->y;
        for($x = $begin->x; $x <= $end->x; $x++) {
            $point = new Point($steep ? $y : $x, $steep ? $x : $y);
            $canvas->setPixel($point, "black");
            
            $error -= $dy;
            if($error >= 0)
                continue;
    
            $y     += $yStep;
            $error += $dx;
        }
    }
}