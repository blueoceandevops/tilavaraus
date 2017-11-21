const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
	devtool: 'source-map',
	entry: {
		'dist/detail': './src/detail.js',
		'service-worker': './src/service-worker.js',
		'dist/main': './src/main.js'
	},
	output: {
		filename: '[name].js',
		path: path.resolve(__dirname, '../../../target/classes/static/')
	},
	module: {
		rules: [
			{
				test: /\.scss$/,
				use: ExtractTextPlugin.extract({
					use: [{loader: 'css-loader', options: {minimize: true}}, {loader: 'sass-loader'}],
					fallback: 'style-loader'
				})
			},
			{
				test: /\.css$/,
				use: ExtractTextPlugin.extract({
					fallback: 'style-loader',
					use: [{loader: 'css-loader', options: {minimize: true}}]
				})
			}
		]
	},
	plugins: [
		new ExtractTextPlugin({
			filename: '[name].css'
		}),
		new webpack.optimize.CommonsChunkPlugin({
			name: 'dist/main',
			chunks: ['dist/main', 'dist/detail']
		}),
		new UglifyJSPlugin({
			sourceMap: true
		}),
		new webpack.DefinePlugin({
			PRECACHE: JSON.stringify(new Date().toISOString())
		})
	]
};