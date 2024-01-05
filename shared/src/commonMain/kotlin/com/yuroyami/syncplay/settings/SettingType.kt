package com.yuroyami.syncplay.settings

/** Defines the type of a setting (preference).
 * @property [OneClickSettingType]
 * @property [MultiChoicePopupSettingType]
 * @property [CheckboxSettingType]
 * @property [ToggleSettingType]
 * @property [SliderSettingType]
 * @property [PopupSettingType]
 * @property [ColorSettingType]
 * @property [TextFieldSettingType]
 */
enum class SettingType {
    /** OneClickSetting is a [SettingType] for a setting whose function is based upon one click only.
     * That means, that the setting will not read/write any value,
     * but instead, just initiates a lambda function (onClick).
     *
     * In XML Preferences, this is equivalent to the plain [Preference] setting. */
    OneClickSettingType,

    /** MultiChoicePopupSetting is a [SettingType] for a setting that shows a multi-choice popup dialog.
     * This type of setting receives a list of entry pairs, and only one can be chosen.
     *
     * In XML preferences, this is equivalent to [ListPreference] setting.*/
    MultiChoicePopupSettingType,

    /** CheckboxSetting is a [SettingType] for a setting that can be checked on/off, with a checbox.
     * This means it works with booleans.
     *
     * In XML preferences, this is equivalent to [CheckBoxPreference] setting. */
    CheckboxSettingType,

    /** Basically the same as [CheckboxSettingType] but uses a toggle button instead.
     *
     * In XML preferences, this is equivalent to [SwitchPreference] setting. */
    ToggleSettingType,

    /** SliderSetting is a [SettingType] that uses a slider, works with integers.
     *
     * In XML preferences, this is equivalent to [SeekBarPreference] setting. */
    SliderSettingType,

    /** PopupSetting is a [SettingType] for a setting whose function is to show a popup.
     * The popup does not return any value, and has custom content (Composable).
     * This is entirely different from [MultiChoicePopupSettingType], this is closer in nature
     * to [OneClickSettingType] but takes a [Composable] parameter rather than an [onClick].
     *
     * This has no direct counterpart in XML preferences. */
    PopupSettingType,

    /** Allows the user to choose/pick a color, which is saved as a hex code string.
     *
     * This has no direct counterpart in XML preferences, but exists in various libraries
     * such as jaredrummler's colorpicker library (which was used in Syncplay-android version 0.10.1 */
    ColorSettingType,

    /** Allows the user to input a string  */
    TextFieldSettingType
}