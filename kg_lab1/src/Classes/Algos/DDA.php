<?php

namespace lab\Classes\Algos;

use lab\Classes\Point;
use lab\Interfaces\ICanvas;
use lab\Interfaces\ILineAlgo;

class DDA implements ILineAlgo
{
    function drawLine(ICanvas $canvas, Point $begin, Point $end): void
    {
        $l = max(abs($end->x - $begin->x), abs($end->y - $begin->y));
        $dx = ($end->x - $begin->x) / $l;
        $dy = ($end->y - $begin->y) / $l;
        
        $canvas->setPixel(new Point($x = $begin->x, $y = $begin->y), "black");
        $canvas->setPixel(new Point($end->x, $end->y), "black");
        
        for($i = 0; $i < $l; $i++)
            $canvas->setPixel(new Point(round($x += $dx), round($y += $dy)), "black");
    }
}