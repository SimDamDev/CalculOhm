package com.simdamdev.calculohm.utils


/**
 * State -  holds the state information for checkbox, unit and value
 * @property checkbox holds the state of checkbox
 * @property unit holds the state of unit
 * @property value holds the state of value
 */
class State(
    val checkbox: CheckboxState = CheckboxState(),
    val unit: UnitState = UnitState(),
    val value: ValueState = ValueState(unit= unit),
)