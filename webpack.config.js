const JS_PATH = __dirname + "/WebContent/js/";
module.exports = {
	entry: JS_PATH + "index.js",
	output: {
		path: JS_PATH,
		filename: "compiled.js"
	},
	module : {
		loaders: ['babel']
	}
}