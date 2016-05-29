<?php

namespace App\Http\Middleware;

use Closure;

class Ajax
{

    public function handle($request, Closure $next)
    {
        if ($request->ajax())
        {
            return $next($request);
        }

        abort(404);
    }

}