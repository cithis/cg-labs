<?php

namespace lab\Interfaces;

use lab\Classes\Point;

interface ILineAlgo
{
    function drawLine(ICanvas $canvas, Point $begin, Point $end): void;
}