module.exports = {
    "parser": "@typescript-eslint/parser",
    "extends": [
        "plugin:@typescript-eslint/recommended" // Uses the recommended rules from the @typescript-eslint/eslint-plugin
    ],
    "env": {
        "browser": true,
        "node": true,
        "jest": true,
        "es6": true
    },
    "plugins": [
    ],
    "parserOptions": {
        "ecmaVersion": 2018,
        "sourceType": "module",
        "ecmaFeatures": {
            "jsx": true
        }
    },
    "globals": {
        "__DEBUG__": false,
        "__DEV__": false
    },
    "settings": {
        "import/resolver": {
            "node": {
                "paths": ["src"],
                "extensions": [".js"]
            }
        }
    },
    "rules": {
        "arrow-parens": [
            "error",
            "always"
        ],
        "arrow-body-style": [
            "error",
            "as-needed"
        ],
        "comma-dangle": [
            "error",
            "never"
        ],
        "brace-style": [
            "error",
            "stroustrup"
        ],
        "function-paren-newline": [
            "error",
            "consistent"
        ],
        "no-mixed-operators": [
            "error",
            {
                "groups": [
                    [
                        "&",
                        "|",
                        "^",
                        "~",
                        "<<",
                        ">>",
                        ">>>"
                    ],
                    [
                        "==",
                        "!=",
                        "===",
                        "!==",
                        ">",
                        ">=",
                        "<",
                        "<="
                    ],
                    [
                        "&&",
                        "||"
                    ],
                    [
                        "in",
                        "instanceof"
                    ]
                ],
                "allowSamePrecedence": true
            }
        ],
        "guard-for-in": 0,
        "no-plusplus": "off",
        "no-restricted-syntax": "off",
        "import/imports-first": 0,
        "import/newline-after-import": 0,
        "import/no-dynamic-require": 0,
        "import/no-extraneous-dependencies": 0,
        "import/no-named-as-default": 0,
        "import/no-unresolved": 2,
        "import/prefer-default-export": 0,
        "indent": [
            "error",
            4,
            {
                "SwitchCase": 1
            }
        ],
        "generator-star-spacing": [
            "error",
            {
                "before": true,
                "after": true
            }
        ],
        "max-len": 0,
        "newline-per-chained-call": 0,
        "no-confusing-arrow": 0,
        "no-console": 1,
        "no-use-before-define": 0,
        "prefer-template": 2,
        "class-methods-use-this": 0,
        "no-param-reassign": [
            "error",
            {
                "props": false
            }
        ],
        "require-yield": 0,
        "import/no-webpack-loader-syntax": 0,
        "object-curly-newline": 0,
        "default-case": 0,
        "no-shadow": 0,
        "array-callback-return": 0,
        "consistent-return": 0,
        "no-underscore-dangle": "off",
        "no-unused-expressions": ["error", { "allowTernary": true }]
    }
}
