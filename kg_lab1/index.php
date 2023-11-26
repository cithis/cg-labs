<?php
require "vendor/autoload.php";

use lab\Classes\{Drawing, GdCanvasImpl, ImagickCanvasImpl, Algos, Point};

$algo = match ($_GET["algo"] ?? NULL) {
    'dda'       => new Algos\DDA,
    'wu'        => new Algos\Wu,
    default     => new Algos\BresenhamLine,
};

$canvas  = new ImagickCanvasImpl(128, 436, 4);
$drawing = new Drawing($canvas, $algo, new Algos\BresenhamCircle);

$instructions = json_decode(file_get_contents("src/instructions.json"));
foreach($instructions as $ix) {
    if($ix[0] == 'L') {
        $drawing->drawLine(new Point($ix[1][0], $ix[1][1]), new Point($ix[2][0], $ix[2][1]));
    } else {
        $drawing->drawCircle(new Point($ix[1][0], $ix[1][1]), $ix[2]);
    }
}

$blob = "data:image/png;base64," . base64_encode($drawing->getResult());
?>

<style>
    table td {
        border: 5px inset;
    }

    table {
        border-collapse: collapse;
    }
</style>

<table>
    <tr>
        <td><a href="?algo=bresenham">Брезенхем</a></td>
        <td><a href="?algo=dda">ДДА</a></td>
        <td><a href="?algo=wu">Ву</a></td>
    </tr>
</table>

<hr/>

<table>
    <tr>
        <td colspan="3">
            <img src="<?= $blob ?>"  alt="amogus"/>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <center>
                АЛГОРИТМ: <b><?php
                    echo match($_GET["algo"] ?? NULL) {
                        'dda'       => "DDA",
                        'wu'        => "Ву",
                        default     => "Брезенхема",
                    }
                ?></b> <i>+ Брезенхем для кола</i>
            </center>
        </td>
    </tr>
    <tr>
        <td>Лiнiї</td>
        <td>Кола</td>
        <td>Усього</td>
    </tr>
    <tr>
        <td><?= round($drawing->lineTime, 5, PHP_ROUND_HALF_ODD) ?> s</td>
        <td><?= round($drawing->circleTime, 5, PHP_ROUND_HALF_ODD) ?> s</td>
        <td><?= round(($drawing->lineTime + $drawing->circleTime), 5, PHP_ROUND_HALF_ODD) ?> s</td>
    </tr>
</table>