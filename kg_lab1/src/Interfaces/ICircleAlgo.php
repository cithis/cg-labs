<?php

namespace lab\Interfaces;

use lab\Classes\Point;

interface ICircleAlgo
{
    function drawCircle(ICanvas $canvas, Point $center, int $radius): void;
}