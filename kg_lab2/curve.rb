CurveOptions           = Struct.new(:w, :h, :precision, :scale, :shift, :rotation, :mirror, :flip)
def default_curve_opts = CurveOptions.new(640, 480, 100, 1, [0, 0], 0, false, false)

CurvePoint = Struct.new(:x, :y)

class BezierCurve
  # @param [Array<CurvePoint>] control_points
  def initialize(control_points)
    @control_points = control_points
  end

  # @param [CurveOptions] options
  # @return [Array<CurvePoint>]
  def get_points(options)
    points = Array.new
    dt = 1.0 / options.precision
    t = 0.0
    while true do
      point = CurvePoint.new(0, 0)

      # Calculate point
      @control_points.each_with_index do |cp, i|
        basis = beizer_basis(i, @control_points.length - 1, t)
        point.x += cp.x * basis
        point.y += cp.y * basis
      end

      # Apply transformations
      point.y = options.h - point.y if options.flip
      point.x = options.w - point.x if options.mirror

      if options.scale != 1
        point.x *= options.scale
        point.y *= options.scale
      end

      if options.shift != [0, 0]
        point.x += options.shift[0]
        point.y += options.shift[1]
      end

      if options.rotation != 0
        angle = options.rotation * (Math::PI / 180)
        point.x = point.x * Math::cos(angle) - point.y * Math::sin(angle)
        point.y = point.x * Math::sin(angle) + point.y * Math::cos(angle)
      end

      point.x = [options.w, ([0, point.x].max)].min
      point.y = [options.h, ([0, point.y].max)].min

      # Push point to array
      points.push(point)

      if t == 1.0
        break
      else
        t = [1.0, (t + dt)].min
      end
    end

    return points
  end

  protected

  def beizer_basis(i, n, t)
    comb = (1..n).to_a.combination(i).size
    comb * (t ** (n - i)) * (1 - t) ** i
  end
end