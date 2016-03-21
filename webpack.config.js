const JS_PATH = __dirname + "/WebContent/js/";
module.exports = {
	entry: JS_PATH + "index.js",
	output: {
		path: JS_PATH,
		filename: "compiled.js"
	},
	module: {
		loaders: [
			{
				test: /\.js$/,
				loader: "babel-loader",
				exclude: /node_modules/,
				query: {
					presets: ['react', 'es2015']
				}
			}
		]
	},
	devtool: 'eval-source-map'
}