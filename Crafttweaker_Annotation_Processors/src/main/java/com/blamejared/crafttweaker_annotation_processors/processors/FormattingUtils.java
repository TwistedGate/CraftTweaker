package com.blamejared.crafttweaker_annotation_processors.processors;

import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

class FormattingUtils {


	static String getOperatorFormat(ZenCodeType.OperatorType operator) {
		switch (operator) {
			case ADD:
				return "%s + %s";
			case SUB:
				return "%s - %s";
			case MUL:
				return "%s * %s";
			case DIV:
				return "%s / %s";
			case MOD:
				return "%s % %s";
			case CAT:
				return "%s ~ %s";
			case OR:
				return "%s | %s";
			case AND:
				return "%s & %s";
			case XOR:
				return "%s ^ %s";
			case NEG:
				return "-%s";
			case INVERT:
				return "~%s";
			case NOT:
				return "!%s";
			case INDEXSET:
				return "[%s] = %s";
			case INDEXGET:
				return "[%s]";
			case CONTAINS:
				return "%s in %s";
			case COMPARE:
				return "%s compare %s";
			case MEMBERGETTER:
				return "%s.%s";
			case MEMBERSETTER:
				return "%s.%s = %s";
			case EQUALS:
				return "%s == %s";
			case NOTEQUALS:
				return "%s != %s";
			case SHL:
				return "%s << %s";
			case SHR:
				return "%s >> %s";
			case ADDASSIGN:
				return "%s += %s";
			case SUBASSIGN:
				return "%s -= %s";
			case MULASSIGN:
				return "%s *= %s";
			case DIVASSIGN:
				return "%s /= %s";
			case MODASSIGN:
				return "%s %= %s";
			case CATASSIGN:
				return "%s ~= %s";
			case ORASSIGN:
				return "%s |= %s";
			case ANDASSIGN:
				return "%s &= %s";
			case XORASSIGN:
				return "%s ^= %s";
			case SHLASSIGN:
				return "%s <<= %s";
			case SHRASSIGN:
				return "%s >>= %s";

			default:
				return "";
		}
	}

	static int getOperandCountFor(ZenCodeType.OperatorType operator) {
		switch (operator) {
			//Unary Operators
			case NEG:
			case INVERT:
			case NOT:
				return 1;

			//Binary Operators
			case INDEXGET:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
			case CAT:
			case OR:
			case AND:
			case XOR:
			case CONTAINS:
			case COMPARE:
			case MEMBERGETTER:
			case EQUALS:
			case NOTEQUALS:
			case SHL:
			case SHR:
			case ADDASSIGN:
			case SUBASSIGN:
			case MULASSIGN:
			case DIVASSIGN:
			case MODASSIGN:
			case CATASSIGN:
			case ORASSIGN:
			case ANDASSIGN:
			case XORASSIGN:
			case SHLASSIGN:
			case SHRASSIGN:
				return 2;

			//Ternary Operators
			case INDEXSET:
			case MEMBERSETTER:
				return 3;

			default:
				return 0;
		}
	}

	/**
	 * Formats the parameter to fit ZS.
	 */
	static String convertTypeName(TypeMirror typeMirror, Types typeUtils) {
		if(typeMirror.getKind().isPrimitive())
			return typeMirror.toString();

		final ZenCodeType.Name annotation = typeUtils.asElement(typeMirror).getAnnotation(ZenCodeType.Name.class);
		if (annotation != null) {
			return annotation.value();
		}

		if(typeMirror instanceof DeclaredType) {
			DeclaredType declaredType = (DeclaredType) typeMirror;
			final String typeString = declaredType.toString().toLowerCase();
			if(typeString.startsWith("java.util.map<")) {
				final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
				final String valueType = convertTypeName(typeArguments.get(1), typeUtils);
				final String keyType = convertTypeName(typeArguments.get(0), typeUtils);
				return String.format("%s[%s]", valueType, keyType);
			} else if(typeString.startsWith("java.util.list<")) {
				final String elementType = convertTypeName(declaredType.getTypeArguments().get(0), typeUtils);
				return String.format("%s[]", elementType);
			}
		}



		final String s = typeMirror.toString();
		if (s.toLowerCase().startsWith("java.lang."))
			return s.substring("java.lang.".length());

		return s;
	}

}