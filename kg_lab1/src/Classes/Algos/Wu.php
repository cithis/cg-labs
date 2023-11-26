<?php

namespace lab\Classes\Algos;

use lab\Classes\Point;
use lab\Interfaces\ICanvas;
use lab\Interfaces\ILineAlgo;

class Wu implements ILineAlgo
{
    private function fpart(float $x): float
    {
        return $x - floor($x);
    }
    
    private function rfpart(float $x): float
    {
        return 1.0 - $this->fpart($x);
    }
    
    private function color(float $intensity): string
    {
        $intensity = round($intensity, 2, PHP_ROUND_HALF_ODD);
        
        return "rgba(0, 0, 0, $intensity)";
    }
    
    function drawLine(ICanvas $canvas, Point $begin, Point $end): void
    {
        $steep = abs($end->y - $begin->y) > abs($end->x - $begin->x);
        if($steep) {
            [$begin->x, $begin->y] = [$begin->y, $begin->x];
            [$end->x, $end->y]     = [$end->y, $end->x];
        }
    
        if($begin->x > $end->x) {
            [$begin->x, $end->x] = [$end->x, $begin->x];
            [$begin->y, $end->y] = [$end->y, $begin->y];
        }
    
        $dx = $end->x - $begin->x;
        $dy = $end->y - $begin->y;
        
        $gradient = ($dx == 0) ? 1 : ($dy / $dx);
        
        $x1 = $begin->x;
        $x2 = $end->x;
        $iY = $begin->y;
    
        for($x = $x1; $x <= $x2; $x++) {
            if($steep) {
                $canvas->setPixel(new Point(floor($iY), $x), $this->color($this->rfpart($iY)));
                $canvas->setPixel(new Point(floor($iY) + 1, $x), $this->color($this->fpart($iY)));
            } else {
                $canvas->setPixel(new Point($x, floor($iY)), $this->color($this->rfpart($iY)));
                $canvas->setPixel(new Point($x, floor($iY) + 1), $this->color($this->fpart($iY)));
            }
        
            $iY += $gradient;
        }
    }
}