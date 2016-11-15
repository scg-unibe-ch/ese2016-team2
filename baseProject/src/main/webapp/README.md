### FontAwesome
Adjust the fonts path in

<pre>
resources/components/font-awesome/scss/_variables.scss
</pre>
to:

<pre>
$fa-font-path:        "../../components/font-awesome/fonts" !default;
</pre>

### Bitters
.. has to be installed via gem now.

<pre>
$ gem install bitters
</pre>

... (you might have to sudo, but think twice).
... then in the resources/components directory

<pre>
$ bitters install
</pre>

... in app.scss, bitters is imported by

<pre>
@import "base/base";
</pre>

... after bourbon.

### jquery-latitude-longitude-picker-gmaps

... n/a via bower, but via npm (see package.json)
