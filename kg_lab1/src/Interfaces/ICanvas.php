<?php

namespace lab\Interfaces;

use lab\Classes\Point;

interface ICanvas
{
    function getHeight(): int;
    function getWidth(): int;
    
    function setPixel(Point $at, string $color): void;
    
    function render(): string;
}